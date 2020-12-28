package activity;

public class cartUser {
    public int id;
    public String unique_id;
    public String name;
    public String email;
    public String encrypted_password;
    public String salt;
    public String created_at;
    public String updated_at;
    public String address;
    public String number;




    public cartUser(int id, String unique_id, String name, String email, String encrypted_password, String salt, String address, String number, String created_at,String updated_at) {
        this.id = id;
        this.unique_id=unique_id;
        this.name = name;
        this.email = email;
        this.encrypted_password= encrypted_password;
        this.salt=salt;
        this.created_at=created_at;
        this.updated_at=updated_at;
        this.address=address;
        this.number=number;
    }
    public int getId() {
        return id;
    }

    public String getUnique_id() {
        return unique_id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getEncrypted_password() {
        return encrypted_password;
    }

    public String getSalt() {
        return salt;
    }

    public String getAddress() {
        return address;
    }

    public String getNumber() {
        return number;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }
}
