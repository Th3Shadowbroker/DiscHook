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

public class DiscordAPIResponse
{
    private final int rateRemaining;

    private final int rateLimit;

    private final long rateReset;


    private final boolean success;

    private final int responseCode;


    private final long retryAfter;


    public DiscordAPIResponse(int rateRemaining, int rateLimit, long rateReset, long retryAfter, int responseCode)
    {
        this.rateRemaining = rateRemaining;
        this.rateLimit = rateLimit;
        this.rateReset = rateReset;
        this.responseCode = responseCode;
        this.retryAfter = retryAfter;
        this.success = responseCode == 200 || responseCode == 204;
    }

    public int getRateRemaining()
    {
        return rateRemaining;
    }

    public int getRateLimit()
    {
        return rateLimit;
    }

    public long getRateReset()
    {
        return rateReset;
    }

    public boolean isSuccess()
    {
        return success;
    }

    public boolean isRateLimitExceeded()
    {
        return responseCode == 429;
    }

    public int getResponseCode()
    {
        return responseCode;
    }

    public long getRetryAfter()
    {
        return retryAfter;
    }
}
