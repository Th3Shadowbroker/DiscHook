/*
 * Copyright (c) 2019 M4taiori
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.m4taiori.discord.dischook.util;

import io.m4taiori.discord.dischook.DiscordWebhook;
import io.m4taiori.discord.dischook.events.WebhookPendingEvent;
import io.m4taiori.discord.dischook.events.WebhookSentEvent;
import io.m4taiori.discord.dischook.events.WebhookSendingEvent;
import io.m4taiori.discord.dischook.exceptions.InvalidResponseException;
import io.m4taiori.discord.dischook.exceptions.QueueResetException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PacketQueue
{

    private final DiscordWebhook hook;

    private final List<DiscordWebhookPacket> queue = new ArrayList<>();

    public PacketQueue(DiscordWebhook hook)
    {
        this.hook = hook;
    }

    public void queue(DiscordWebhookPacket... packets)
    {
        for ( DiscordWebhookPacket packet : packets ) queue.add(packet);

        try
        {
            sendQueued();
        } catch (QueueResetException | InvalidResponseException e) {
            e.printStackTrace();
        }
    }

    public void sendQueued() throws QueueResetException, InvalidResponseException
    {
        Iterator<DiscordWebhookPacket> queueI = queue.iterator();

        while (queueI.hasNext())
        {
            DiscordWebhookPacket packet = queueI.next();
            hook.getEventManager().post( new WebhookSendingEvent(packet, hook));

            DiscordAPIResponse response = DiscordAPI.sendHookRequest(hook, packet);

            if (response.isSuccess())
            {
                queueI.remove();
                hook.getEventManager().post( new WebhookSentEvent(response, packet, hook) );
            }
            else if (response.isRateLimitExceeded())
            {
                queue.forEach( pendingPacket -> hook.getEventManager().post( new WebhookPendingEvent(pendingPacket, hook) ) );

                try
                {
                    Thread.sleep(response.getRetryAfter());
                    sendQueued();
                } catch (InterruptedException e) {
                    throw new QueueResetException(hook, packet, response);
                }

                return;
            } else {
                throw new InvalidResponseException(hook, packet, response);
            }
        }

    }
}
