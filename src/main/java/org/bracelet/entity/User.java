package org.bracelet.entity;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.persistence.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 用户实体
 */
@Entity
@Table(name = "user")
public class User {

    /**
     * 用户ID
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * 用户名
     */
    @Column(name = "username", length = 128, nullable = false)
    private String username;

    /**
     * 用户口令
     */
    @Column(name = "password", nullable = false)
    private String password;

    /**
     * 姓名
     */
    @Column(name = "name", length = 64)
    private String name;

    /**
     * 性别
     */
    @Column(name = "sex", length = 10)
    private String sex;

    /**
     * 生日
     */
    @Column(name = "birthday")
    private Date birthday;

    /**
     * 年龄
     */
    @Column(name = "age")
    private Integer age;

    /**
     * 体重
     */
    @Column(name = "weight", scale = 2)
    private Double weight;

    /**
     * 身高
     */
    @Column(name = "height", scale = 2)
    private Double height;

    /**
     * 手机号
     */
    @Column(name = "phone", length = 20, unique = true, nullable = false)
    private String phone;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "registerTime")
    private java.util.Date registerTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "lastLoginTime")
    private java.util.Date lastLoginTime;

    /**
     * 用户饮食偏好
     */
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_food",
            joinColumns = {@JoinColumn(name = "userId")},
            inverseJoinColumns = {@JoinColumn(name = "foodTypeId")})
    private List<FoodType> likeFoods;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "friends",
            joinColumns = {@JoinColumn(name = "userId")},
            inverseJoinColumns = {@JoinColumn(name = "friendId")})
    private List<User> friends;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "toUserId")
    private List<Message> messages;

    public User() {
        this.likeFoods = new ArrayList<>();
        this.friends = new ArrayList<>();
        messages = new ArrayList<>();
        this.weight = 0.0;
        this.height = 0.0;
        this.age = 0;
        this.sex = "男";
    }

    public User(String jsonString) {
        JSONObject json = JSONObject.fromString(jsonString);
        try {
            this.id = json.getLong("id");
        } catch (Exception e) {
            this.id = null;
        }
        try {
            this.password = json.getString("password");
        } catch (Exception e) {
            this.password = "#";
        }
        this.username = json.getString("username");
        this.name = json.getString("name");
        this.birthday = new Date(json.getLong("birthday"));
        this.age = json.getInt("age");
        this.sex = json.getString("sex");
        this.weight = json.getDouble("weight");
        this.height = json.getDouble("height");
        this.phone = json.getString("phone");
        this.registerTime = new java.util.Date(json.getLong("registerTime"));
        this.lastLoginTime = new java.util.Date(json.getLong("lastLoginTime"));
        this.likeFoods = new ArrayList<>();
        JSONArray foodArray = json.getJSONArray("likeFoods");
        for (Iterator it = foodArray.iterator(); it.hasNext(); ) {
            likeFoods.add(new FoodType(((JSONObject) it.next()).toString()));
        }
        this.friends = new ArrayList<>();
        JSONArray friendArray = json.getJSONArray("friends");
        for (Iterator it = friendArray.iterator(); it.hasNext(); ) {
            JSONObject friend = (JSONObject) it.next();
            User user = new User();
            user.setId(friend.getLong("id"));
            user.setUsername(friend.getString("username"));
            user.setName(friend.getString("name"));
            user.setSex(friend.getString("sex"));
            user.setBirthday(new Date(friend.getLong("birthday")));
            user.setAge(friend.getInt("age"));
            user.setPhone(friend.getString("phone"));
            friends.add(user);
        }
        this.messages = new ArrayList<>();
        JSONArray messageArray = json.getJSONArray("messages");
        for (Iterator it = messageArray.iterator(); it.hasNext(); ) {
            messages.add(new Message(((JSONObject) it.next()).toString()));
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<FoodType> getLikeFoods() {
        return likeFoods;
    }

    public void setLikeFoods(List<FoodType> likeFoods) {
        this.likeFoods = likeFoods;
    }

    public java.util.Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(java.util.Date registerTime) {
        this.registerTime = registerTime;
    }

    public java.util.Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(java.util.Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public List<User> getFriends() {
        return friends;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    @Override
    public String toString() {
        JSONObject json = new JSONObject();
        json.put("id", id)
                .put("username", username)
                .put("name", name)
                .put("password", password)
                .put("sex", sex)
                .put("birthday", birthday.getTime())
                .put("age", age)
                .put("weight", weight)
                .put("height", height)
                .put("phone", phone)
                .put("registerTime", registerTime.getTime())
                .put("lastLoginTime", lastLoginTime.getTime());
        JSONArray foodArray = new JSONArray();
        for (FoodType foodType : likeFoods) {
            foodArray.put(JSONObject.fromString(foodType.toString()));
        }
        json.put("likeFoods", foodArray);

        JSONArray friendArray = new JSONArray();
        for (User user : friends) {
            JSONObject u = new JSONObject();
            u.put("id", user.getId())
                    .put("username", user.getUsername())
                    .put("name", user.getName())
                    .put("sex", user.getSex())
                    .put("birthday", user.getBirthday().getTime())
                    .put("age", user.getAge())
                    .put("phone", user.getPhone());
            friendArray.put(u);
        }
        json.put("friends", friendArray);

        JSONArray messageArray = new JSONArray();
        for (Message message : messages) {
            messageArray.put(JSONObject.fromString(message.toString()));
        }
        json.put("messages", messageArray);
        return json.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (id != null ? !id.equals(user.id) : user.id != null) return false;
        if (username != null ? !username.equals(user.username) : user.username != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (name != null ? !name.equals(user.name) : user.name != null) return false;
        if (sex != null ? !sex.equals(user.sex) : user.sex != null) return false;
        if (birthday != null ? !birthday.equals(user.birthday) : user.birthday != null) return false;
        if (age != null ? !age.equals(user.age) : user.age != null) return false;
        if (weight != null ? !weight.equals(user.weight) : user.weight != null) return false;
        if (height != null ? !height.equals(user.height) : user.height != null) return false;
        if (phone != null ? !phone.equals(user.phone) : user.phone != null) return false;
        if (registerTime != null ? !registerTime.equals(user.registerTime) : user.registerTime != null) return false;
        if (lastLoginTime != null ? !lastLoginTime.equals(user.lastLoginTime) : user.lastLoginTime != null)
            return false;
        if (likeFoods != null ? !likeFoods.equals(user.likeFoods) : user.likeFoods != null) return false;
        if (friends != null ? !friends.equals(user.friends) : user.friends != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (sex != null ? sex.hashCode() : 0);
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        result = 31 * result + (age != null ? age.hashCode() : 0);
        result = 31 * result + (weight != null ? weight.hashCode() : 0);
        result = 31 * result + (height != null ? height.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (registerTime != null ? registerTime.hashCode() : 0);
        result = 31 * result + (lastLoginTime != null ? lastLoginTime.hashCode() : 0);
        result = 31 * result + (likeFoods != null ? likeFoods.hashCode() : 0);
        result = 31 * result + (friends != null ? friends.hashCode() : 0);
        return result;
    }
}
