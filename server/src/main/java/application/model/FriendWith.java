package application.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "friend_with", schema = "public")
public class FriendWith {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id_1", referencedColumnName = "user_id")
    private User user1;

    @ManyToOne
    @JoinColumn(name = "user_id_2", referencedColumnName = "user_id")
    private User user2;

    @Column(name = "user_id_1", insertable = false, updatable = false)
    private int userId1;

    @Column(name = "user_id_2", insertable = false, updatable = false)
    private int userId2;

    public FriendWith(int userId1, int userId2) {
        this.userId1 = userId1;
        this.userId2 = userId2;
    }

    public FriendWith() {
    }

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public User getUser2() {
        return user2;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }

    public int getId() {
        return id;
    }

    public int getUser_id_1() {
        return userId1;
    }

    public void setUser_id_1(int userId1) {
        this.userId1 = userId1;
    }

    public int getUser_id_2() {
        return userId2;
    }

    public void setUser_id_2(int userId2) {
        this.userId2 = userId2;
    }

    /**
     * TODO: JavaDoc.
     */
    public String toString() {
        return "FriendWith{"
                + "id=" + id
                + ", user_id_1=" + userId1
                + ", user_id_2=" + userId2
                + '}';
    }

}
