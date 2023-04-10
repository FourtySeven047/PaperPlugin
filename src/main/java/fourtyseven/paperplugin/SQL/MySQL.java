package fourtyseven.paperplugin.SQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL {

    //https://www.digitalocean.com/community/tutorials/how-to-install-linux-apache-mysql-php-lamp-stack-on-ubuntu-22-04
    //Habe das Default Password also "password" gewaehlt
    //ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'password';
    private String host = "localhost";
    private String port = "3306";
    private String database = "paper_server"; //minecraft_server auf pc, arena auf Laptop
    private String username = "root";
    private String password = "";

    private Connection connection;

    public boolean isConnected(){
        return(connection == null ? false : true);
    }

    public void connect() throws ClassNotFoundException, SQLException {
        if(!isConnected()){
            connection = DriverManager.getConnection("jdbc:mysql://" +
                            host + ":" + port + "/" + database + "?useSSL=false",
                    username, password);
        }
    }

    public void disconnect(){
        if(isConnected()){
            try {
                connection.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }

    public Connection getConnection(){
        return connection;
    }
}
