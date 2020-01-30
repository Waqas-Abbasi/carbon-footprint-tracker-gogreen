package application.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "activity_type", schema = "public")
public class ActivityType {

    @Id
    @Column(name = "type_id", unique = true)
    private int id;

    @Column(name = "name")
    private String name;

    public ActivityType() {
    }

    protected ActivityType(int typeId, String name) {
        this.id = typeId;
        this.name = name;
    }

    public int getType_id() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "ActivityType{"
                + "id=" + id
                + ", name='" + name + '\''
                + '}';
    }
}
