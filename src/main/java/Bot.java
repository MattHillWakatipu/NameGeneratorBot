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

    public static void main(String[] args) {

        String token = getToken();

        GatewayDiscordClient client = createClient(token);

        login(client);

        client.getEventDispatcher().on(MessageCreateEvent.class)
                .map(MessageCreateEvent::getMessage)
                .filter(message -> message.getAuthor().map(user -> !user.isBot()).orElse(false))
                .filter(message -> message.getContent().equalsIgnoreCase("!ping"))
                .flatMap(Message::getChannel)
                .flatMap(channel -> channel.createMessage("Pong!"))
                .subscribe();

        client.onDisconnect().block();
    }

    //@org.jetbrains.annotations.Nullable
    private static GatewayDiscordClient createClient(String token) {
        GatewayDiscordClient client = DiscordClientBuilder.create(token)
                .build()
                .login()
                .block();
        return client;
    }

    private static void login(GatewayDiscordClient client) {
        client.getEventDispatcher().on(ReadyEvent.class)
                .subscribe(event -> {
                    User self = event.getSelf();
                    System.out.println(String.format("Logged in as %s#%s", self.getUsername(), self.getDiscriminator()));
                });
    }

    /**
     * Get
     *
     * @return
     */
    private static String getToken() {
        try {
            File file = new File("C:\\Users\\Matt\\IdeaProjects\\NameGeneratorBot\\src\\main\\resources\\Token.txt"); //FIXME use path from resource root
            FileReader fileReader = new FileReader(file);
            Scanner scanner = new Scanner(fileReader);
            return scanner.next();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }
}