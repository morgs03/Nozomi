package io.github.adorableskullmaster.nozomi.features.hooks;

import io.github.adorableskullmaster.nozomi.Bot;
import io.github.adorableskullmaster.nozomi.core.database.ConfigurationDataSource;
import io.github.adorableskullmaster.nozomi.core.database.models.Configuration;
import io.github.adorableskullmaster.nozomi.core.util.Utility;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.guild.GuildJoinEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class GenericListener extends ListenerAdapter {

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        Long mainChannel = ConfigurationDataSource.getConfiguration().getMainChannel();
                event.getJDA()
                        .getTextChannelById(mainChannel)
                        .sendMessage(getJoinEmbed(event))
                        .queue();
    }

    @Override
    public void onGuildJoin(GuildJoinEvent event) {
        super.onGuildJoin(event);
    }

    private Message getJoinEmbed(GuildMemberJoinEvent event) {
        Configuration configuration = ConfigurationDataSource.getConfiguration();

        MessageBuilder messageBuilder = new MessageBuilder();
        messageBuilder.setContent(String.format(event.getUser().getAsMention() + ", welcome to %s!", event.getGuild().getName()));
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(Utility.getGuildSpecificRoleColor(event))
                .setAuthor(event.getGuild().getName(), "https://politicsandwar.com/alliance/id=" + Bot.staticConfiguration.getPWId(), event.getGuild().getIconUrl())
                .setDescription(configuration.getJoinText());
        messageBuilder.setEmbed(embedBuilder.build());
        return messageBuilder.build();
    }
}
