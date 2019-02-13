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
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.util.Optional;

class DiscordAPI
{

    public static DiscordAPIResponse sendHookRequest(DiscordWebhook hook, DiscordWebhookPacket packet )
    {
        try
        {

            HttpURLConnection connection = (HttpURLConnection) hook.getUrl().openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("User-Agent", "DiscordWebhook/1.0");
            connection.setRequestProperty("Content-Type", packet.getPayload().isPresent() ? "multipart/form-data" : "application/json");

            connection.setDoOutput(true);
            connection.setDoInput(true);

            OutputStream outputStream = connection.getOutputStream();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            outputStreamWriter.write(packet.toString());

            outputStreamWriter.flush();
            outputStreamWriter.close();
            outputStream.close();

            connection.connect();

            Optional<JSONObject> responseBody = Optional.empty();
            if ( connection.getResponseCode() == 429 ) responseBody = Optional.ofNullable( new JSONObject(summarizeStream(connection.getErrorStream())) );

            return new DiscordAPIResponse
                    (
                      connection.getHeaderField("X-RateLimit-Remaining") != null ? Integer.parseInt(connection.getHeaderField("X-RateLimit-Remaining")) : 0,
                      connection.getHeaderField("X-RateLimit-Limit") != null ? Integer.parseInt(connection.getHeaderField("X-RateLimit-Limit")) : 0,
                      connection.getHeaderField("X-RateLimit-Reset") != null ? Integer.parseInt(connection.getHeaderField("X-RateLimit-Reset")) : 0,
                      responseBody.isPresent() ? responseBody.get().getLong("retry_after") : 0,
                      connection.getResponseCode()
                    );

        } catch (IOException e) {

            e.printStackTrace();

        }

        return new DiscordAPIResponse(0,0,0,0, 0);
    }

    private static String summarizeStream( InputStream stream )
    {
        BufferedReader reader = new BufferedReader( new InputStreamReader(stream) );
        StringBuilder out = new StringBuilder();

        try
        {
            String tmp;
            while ( (tmp = reader.readLine()) != null )
            {
                out.append(tmp);
            }

            return out.toString();
        }
        catch ( Exception ex )
        {
            return null;
        }
    }
}
