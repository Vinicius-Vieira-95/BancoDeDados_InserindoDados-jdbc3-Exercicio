package aplication;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import db.DB;

public class Program {

	public static void main(String[] args) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		Connection conn = null;
		PreparedStatement st = null;
		
		try {
			conn = DB.getConnection();
			
			//Inserir dados na pasta seller no banco de dados
			st = conn.prepareStatement("INSERT INTO seller"
					+" (Name, Email, BirthDate, BaseSalary, DepartmentId)"
					+"VALUE"
					+"(?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			
			//pegando primeiro ponto de interrogação e trocando com dado na sequencia de inserts
			st.setString(1, "Carl Purple");
			//pegando segunda interrogação e colocando email.
			st.setString(2, "Carl@gmail.com");
			//pegando terceiro ponto de interrogação e adicionando da data de nascimento
			st.setDate(3, new java.sql.Date(sdf.parse("22/04/1985").getTime()));
			
			st.setDouble(4, 3000);
			st.setInt(5, 4);
			
			int rowsAffected = st.executeUpdate();
			if(rowsAffected > 0) {
				ResultSet rs =  st.getGeneratedKeys();
				while(rs.next()) {
					int id = rs.getInt(1);
					System.out.println("Done Id = "+id);
				}
			}
			else {
				System.out.println("No rown affected");
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		catch(ParseException e) {
			e.printStackTrace();
		}
		finally {
			DB.closeStatement(st);
			DB.closedConnection();
		}
	}

}
