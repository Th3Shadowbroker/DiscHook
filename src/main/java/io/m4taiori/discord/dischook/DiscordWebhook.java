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

package io.m4taiori.discord.dischook;

import io.m4taiori.discord.dischook.exceptions.InvalidResponseException;
import io.m4taiori.discord.dischook.exceptions.QueueResetException;
import io.m4taiori.discord.dischook.util.DiscordWebhookPacket;
import io.m4taiori.discord.dischook.util.EventManager;
import io.m4taiori.discord.dischook.util.PacketQueue;

import java.net.MalformedURLException;
import java.net.URL;

public class DiscordWebhook
{
    private final URL url;

    private final EventManager eventManager;

    private final PacketQueue packetQueue;

    public DiscordWebhook( String url ) throws MalformedURLException
    {
        this.url = new URL(url);
        this.eventManager = new EventManager();
        this.packetQueue = new PacketQueue(this);
    }

    public URL getUrl()
    {
        return url;
    }

    public EventManager getEventManager()
    {
        return eventManager;
    }

    public PacketQueue getPacketQueue()
    {
        return packetQueue;
    }

    public void sendPacket(DiscordWebhookPacket... packets) throws InvalidResponseException, QueueResetException
    {
        for ( DiscordWebhookPacket packet : packets )
        {
            packetQueue.queue(packet);
        }
        packetQueue.sendQueued();
    }

    public boolean trySendPacket(DiscordWebhookPacket... packets)
    {
        try
        {
            sendPacket(packets);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
