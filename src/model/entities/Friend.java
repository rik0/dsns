/* Copyright */

package model.entities;

/**
 * User: enrico
 * Package: model
 * Date: 9/1/11
 * Time: 10:22 AM
 */
public class Friend {
    String nick;

    public Friend(final String nick) {
        this.nick = nick;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(final String nick) {
        this.nick = nick;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Friend");
        sb.append("{nick='").append(nick).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Friend friend = (Friend) o;

        if (nick != null ? !nick.equals(friend.nick) : friend.nick != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return nick != null ? nick.hashCode() : 0;
    }
}
