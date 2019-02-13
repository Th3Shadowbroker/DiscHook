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

package io.m4taiori.discord.dischook.exceptions;

import io.m4taiori.discord.dischook.DiscordWebhook;
import io.m4taiori.discord.dischook.util.DiscordAPIResponse;
import io.m4taiori.discord.dischook.util.DiscordWebhookPacket;

public abstract class WebhookException extends Exception
{
    private final DiscordWebhook hook;

    private final DiscordWebhookPacket packet;

    private final DiscordAPIResponse response;

    public WebhookException(DiscordWebhook hook, DiscordWebhookPacket packet, DiscordAPIResponse response)
    {
        this.hook = hook;
        this.packet = packet;
        this.response = response;
    }

    public DiscordWebhook getHook()
    {
        return hook;
    }

    public DiscordWebhookPacket getPacket()
    {
        return packet;
    }

    public DiscordAPIResponse getResponse()
    {
        return response;
    }
}
