import NameGenerator.NameGenerator;
import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Bot {

    /**
     * Main method of the bot.
     *
     * @param args Ignored
     */
    public static void main(String[] args) {

        NameGenerator nameGenerator = new NameGenerator();

        String token = getTokenFromFile();

        GatewayDiscordClient client = createClient(token);

        login(client);

        respondToPing(client);

        sayHello(client);

        randomNameF(client, nameGenerator);

        randomNameM(client, nameGenerator);

        randomNameU(client, nameGenerator);

        client.onDisconnect().block();
    }

    /**
     * Read Token.txt for the discord bots token.
     *
     * @return Token as a string.
     */
    private static String getTokenFromFile() {
        try {
            File file = new File("src/main/resources/Token.txt");
            FileReader fileReader = new FileReader(file);
            Scanner scanner = new Scanner(fileReader);
            return scanner.next();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Creates a client from the passed token.
     *
     * @param token Token
     * @return DiscordClient
     */
    private static GatewayDiscordClient createClient(String token) {
        return DiscordClientBuilder.create(token)
                .build()
                .login()
                .block();
    }

    /**
     *
     *
     * @param client Client
     */
    private static void login(GatewayDiscordClient client) {
        client.getEventDispatcher().on(ReadyEvent.class)
                .subscribe(event -> {
                    User self = event.getSelf();
                    System.out.printf("Logged in as %s#%s%n", self.getUsername(), self.getDiscriminator());
                });
    }

    private static void respondToPing(GatewayDiscordClient client) {
        client.getEventDispatcher().on(MessageCreateEvent.class)
                .map(MessageCreateEvent::getMessage)
                .filter(message -> message.getAuthor().map(user -> !user.isBot()).orElse(false))
                .filter(message -> message.getContent().equalsIgnoreCase("!ping"))
                .flatMap(Message::getChannel)
                .flatMap(channel -> channel.createMessage("Pong!"))
                .subscribe();
    }

    private static void sayHello(GatewayDiscordClient client) {
        client.getEventDispatcher().on(MessageCreateEvent.class)
                .map(MessageCreateEvent::getMessage)
                .filter(message -> message.getAuthor().map(user -> !user.isBot()).orElse(false))
                .filter(message -> message.getContent().equalsIgnoreCase("!hello"))
                .flatMap(Message::getChannel)
                .flatMap(channel -> channel.createMessage("Hello!"))
                .subscribe();
    }

    private static void randomNameF(GatewayDiscordClient client, NameGenerator nameGenerator) {
        client.getEventDispatcher().on(MessageCreateEvent.class)
                .map(MessageCreateEvent::getMessage)
                .filter(message -> message.getAuthor().map(user -> !user.isBot()).orElse(false))
                .filter(message -> message.getContent().equalsIgnoreCase("!F Gwynloc"))
                .flatMap(Message::getChannel)
                .flatMap(channel -> channel.createMessage(nameGenerator.genderRegionName('F', "Gwynloc").toString()))
                .subscribe();
    }

    private static void randomNameM(GatewayDiscordClient client, NameGenerator nameGenerator) {
        client.getEventDispatcher().on(MessageCreateEvent.class)
                .map(MessageCreateEvent::getMessage)
                .filter(message -> message.getAuthor().map(user -> !user.isBot()).orElse(false))
                .filter(message -> message.getContent().equalsIgnoreCase("!M Gwynloc"))
                .flatMap(Message::getChannel)
                .flatMap(channel -> channel.createMessage(nameGenerator.genderRegionName('M', "Gwynloc").toString()))
                .subscribe();
    }

    private static void randomNameU(GatewayDiscordClient client, NameGenerator nameGenerator) {
        client.getEventDispatcher().on(MessageCreateEvent.class)
                .map(MessageCreateEvent::getMessage)
                .filter(message -> message.getAuthor().map(user -> !user.isBot()).orElse(false))
                .filter(message -> message.getContent().equalsIgnoreCase("!U Gwynloc"))
                .flatMap(Message::getChannel)
                .flatMap(channel -> channel.createMessage(nameGenerator.genderRegionName('U', "Gwynloc").toString()))
                .subscribe();
    }
}