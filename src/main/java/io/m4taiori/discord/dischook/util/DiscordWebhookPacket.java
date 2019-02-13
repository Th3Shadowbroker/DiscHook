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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Optional;

public class DiscordWebhookPacket
{

    private Optional<String> username;

    private Optional<String> avatarUrl;

    private Optional<String> content;

    private Optional<String> file;

    private Optional<JSONArray> embeds;

    private Optional<JSONObject> payload;

    private boolean tts = false;


    private DiscordWebhookPacket()
    {
        this.username = Optional.empty();
        this.avatarUrl = Optional.empty();
        this.content = Optional.empty();
        this.file = Optional.empty();
        this.embeds = Optional.empty();
        this.payload = Optional.empty();
    }


    public DiscordWebhookPacket setUsername( String username )
    {
        this.username = Optional.ofNullable(username);
        return this;
    }

    public DiscordWebhookPacket setAvatar( String avatarUrl )
    {
        this.avatarUrl = Optional.ofNullable(avatarUrl);
        return this;
    }

    public DiscordWebhookPacket setContent( String content )
    {
        this.content = Optional.ofNullable(content);
        return this;
    }

    public DiscordWebhookPacket setTTS( boolean tts )
    {
        this.tts = tts;
        return this;
    }

    public DiscordWebhookPacket setFile( String fileContent )
    {
        this.file = Optional.ofNullable(fileContent);
        return this;
    }

    public DiscordWebhookPacket attachEmbed( JSONObject embed )
    {
        if (!embeds.isPresent()) embeds = Optional.of( new JSONArray() );
        embeds.get().put(embed);
        return this;
    }

    public DiscordWebhookPacket setPayload( JSONObject payload )
    {
        this.payload = Optional.ofNullable(payload);
        return this;
    }

    public Optional<String> getUsername()
    {
        return username;
    }

    public Optional<String> getAvatarUrl()
    {
        return avatarUrl;
    }

    public Optional<String> getContent()
    {
        return content;
    }

    public Optional<String> getFile()
    {
        return file;
    }

    public Optional<JSONArray> getEmbeds()
    {
        return embeds;
    }

    public Optional<JSONObject> getPayload()
    {
        return payload;
    }

    public boolean isTTS()
    {
        return tts;
    }

    public boolean isValid()
    {
        return content.isPresent() || file.isPresent() || embeds.isPresent();
    }

    public JSONObject toJson()
    {
        JSONObject json = new JSONObject();

        username.ifPresent(s -> json.accumulate("username", s));
        avatarUrl.ifPresent(s -> json.accumulate("avatar_url", s));
        content.ifPresent(s -> json.accumulate("content", s));
        file.ifPresent(s -> json.accumulate("file", s));
        embeds.ifPresent(s -> json.accumulate("embeds", s));
        payload.ifPresent(s -> json.accumulate("payload_json", s));

        json.accumulate("tts", tts);

        return json;
    }

    @Override
    public String toString()
    {
        return toJson().toString();
    }

    public static DiscordWebhookPacket createNew()
    {
        return new DiscordWebhookPacket();
    }

}
