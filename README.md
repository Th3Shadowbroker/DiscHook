## DiscHook
![travis-ci-build-status](https://travis-ci.com/Th3Shadowbroker/DiscHook.svg?branch=master)

DiscHook is a basic implementation of the discord-webhook api. DiscHook uses a really simple
structure and provides basic JSON support ([org.json](https://github.com/stleary/JSON-java)).
I've also implemented basic listener/event support.

### Example implementation
````java
DiscordWebhook hook = new DiscordWebhook("YOUR WEBHOOKS URL");

DiscordWebhookPacket packet = DiscordWebhookPacket.createNew().setUsername("Someone").setContent("Hello World");

hook.trySendPacket( packet );
````
If you want to learn more about how you can implement DiscHook in your project, checkout the wiki.

### Maven
```xml
<repository>
    <id>th3shadowbroker-releases</id>
    <url>https://nexus.m4taiori.io/repository/th3shadowbroker-releases/</url>
</repository>

<dependency>
    <groupId>io.m4taiori.discord</groupId>
    <artifactId>dischook</artifactId>
    <version>1.0.0</version>
</depdency>
```

### Contributing
I made this project in my free time and I don't have the time to maintain it actively. Feel free
to fork this repository and share added features or bug fixes with us through pull-requests.

### License
This project is licensed under the MIT-license.

### Found a bug or one of the four horsemen?
Please use the issue-tool to submit bug-reports or feature requests.

### Contact & support
Any questions, problems or something else? Feel free to contact me via social-media.

[Twitter](https://twitter.com/m4taiori)

[M4taiori.io (Email or Discord)](https://m4taiori.io/#contact)