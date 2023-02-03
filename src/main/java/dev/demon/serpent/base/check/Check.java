package dev.demon.serpent.base.check;

import dev.demon.serpent.Serpent;
import dev.demon.serpent.base.event.Event;
import dev.demon.serpent.base.user.User;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

@Getter
@Setter
public abstract class Check extends Event {

    @Setter
    private User user;
    private CheckData data;
    private double violations;
    private double punishmentVL;
    private String checkName, checkType;
    private boolean enabled;
    private boolean experimental;

    public Check() {
        if (getClass().isAnnotationPresent(CheckData.class)) {
            this.data = getClass().getAnnotation(CheckData.class);


            this.punishmentVL = this.data.punishmentVL();
            this.checkName = this.data.name();
            this.checkType = this.data.type();
            this.enabled = this.data.enabled();
            this.experimental = this.data.experimental();
        }
    }

    public void fail(String... data) {

        this.violations += 1.0;

        StringBuilder stringBuilder = new StringBuilder();

        for (String s : data) {
            stringBuilder.append(s).append(", ");
        }

        String checkType = this.checkType;

        if (!this.experimental) {
            checkType += "*";
        }

        String alert = "Serpent > "
                + this.user.getUserName()
                + " failed "
                + this.checkName
                + " (" + checkType + ")"
                + " (" + this.violations + "/" + this.punishmentVL + ")";

        TextComponent textComponent = new TextComponent(alert);

        textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                new ComponentBuilder(stringBuilder.toString().trim()).create()));

        Serpent.getInstance().getUserManager().getUserMap().entrySet().stream().filter(uuidUserEntry ->
                        uuidUserEntry.getValue().getPlayer().isOp())
                .forEach(uuidUserEntry -> uuidUserEntry.getValue().getPlayer().spigot().sendMessage(textComponent));
    }
}