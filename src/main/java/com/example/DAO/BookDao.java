package com.example.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.example.DBConnection;
import com.example.Models.Book;

public class BookDao {

    private Connection conn = null;
    
    public BookDao()
    {
       conn = DBConnection.getConnection();
       try 
       {
            Statement stmt = conn.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS books " +
                    "(id SERIAL not NULL, " +
                    " title VARCHAR(255), " + 
                    " cover VARCHAR(500), " + 
                    " authorId INT, " + 
                    " categoryId INT," + 
                    " quantity INT ," + 
                    " PRIMARY KEY ( id ),"+
                    " CONSTRAINT fk_author FOREIGN KEY(authorId) REFERENCES authors(id),"+
                    " CONSTRAINT fk_category FOREIGN KEY(categoryId) REFERENCES category(id)"+
                    ");";


            stmt.executeUpdate(sql);
            System.out.println("Created table in given database...");   	  
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
    }

    public void addBook(Book book)
    {
        try {
            String sql = "INSERT INTO books (title,cover,authorId,categoryId,quantity) "
            + "VALUES(?,?,?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getCoverImg());
            preparedStatement.setLong(3, book.getAuthor());
            preparedStatement.setLong(4, book.getCategory());
            preparedStatement.setInt(5, book.getQuantity());

            preparedStatement.executeUpdate();

            System.out.println("Book Inserted successfully ...");

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

       
    }

    public ResultSet getBooks()
    {
        try {
            String sql = "SELECT *"+
            "FROM books b "+
            "INNER JOIN  authors a ON b.authorId = a.id "+
            "INNER JOIN  category c  ON b.categoryId = c.id ;";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            return  preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Book getBook(Long id)
    {
        Book book = null;
        try {
            String sql = "SELECT *"+
            "FROM books b "+
            "INNER JOIN  authors a ON b.authorId = a.id "+
            "INNER JOIN  category c  ON b.categoryId = c.id "+
            "WHERE b.id = ? ;";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setLong(1, id);

            ResultSet rs  =    preparedStatement.executeQuery();
            while (rs.next()) {
 
                 book = new Book((long)(rs.getInt("id")),rs.getString("title"),rs.getString("cover"),rs.getString("firstname") + " " + rs.getString("lastname"),rs.getString("name"),rs.getInt("quantity"));

            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return book ;
    }
    
}
