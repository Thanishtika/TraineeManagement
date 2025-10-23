package DAO;

import DB.DBConnection;
import Model.Trainee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TraineeRepo {
    public boolean saveTrainee(Trainee trainee) {
        String sql = "INSERT INTO Trainee (name, email, department, stipend) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, trainee.getName());
            stmt.setString(2, trainee.getEmail());
            stmt.setString(3, trainee.getDepartment());
            stmt.setDouble(4, trainee.getStipend());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Trainee> getAllTrainee() {
        List<Trainee> list = new ArrayList<>();
        String sql = "SELECT * FROM Trainee";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Trainee trainee = new Trainee();
                trainee.setId(rs.getInt("id"));
                trainee.setName(rs.getString("name"));
                trainee.setEmail(rs.getString("email"));
                trainee.setDepartment(rs.getString("department"));
                trainee.setStipend(rs.getDouble("stipend"));
                list.add(trainee);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Trainee getTraineeById(int id) {
        String sql = "SELECT * FROM Trainee WHERE id = ?";
        Trainee trainee = null;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    trainee = new Trainee();
                    trainee.setId(rs.getInt("id"));
                    trainee.setName(rs.getString("name"));
                    trainee.setEmail(rs.getString("email"));
                    trainee.setDepartment(rs.getString("department"));
                    trainee.setStipend(rs.getDouble("stipend"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trainee;
    }

    public boolean updateTrainee(Trainee trainee) {
        String sql = "UPDATE Trainee SET name=?, email=?, department=?, stipend=? WHERE id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, trainee.getName());
            stmt.setString(2, trainee.getEmail());
            stmt.setString(3, trainee.getDepartment());
            stmt.setDouble(4, trainee.getStipend());
            stmt.setInt(5, trainee.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteTrainee(int id) {
        String sql = "DELETE FROM Trainee WHERE id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

//    public static void saveTrainee(Trainee trainee) throws SQLException, ClassNotFoundException {
//        Connection connection = DBConnection.getConnection();
//        String query = "INSERT INTO Trainee (name,email,department,stipend) VALUES (?,?,?,?)";
//
//        PreparedStatement ps = connection.prepareStatement(query);
//        ps.setString(1, trainee.getName());
//        ps.setString(2, trainee.getEmail());
//        ps.setString(3, trainee.getDepartment());
//        ps.setDouble(4,trainee.getStipend());
//
//        ps.execute();
//    }


//    public Trainee getTraineeById(int id) {
//        String sql = "SELECT * FROM contributions WHERE id = ?";
//        Trainee c = null;
//
//        try (Connection conn = DBConnection.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//
//            stmt.setInt(1, id);
//            try (ResultSet rs = stmt.executeQuery()) {
//                if (rs.next()) {
//                    c = new Trainee();
//                    c.setId(rs.getInt("id"));
//                    c.setName(rs.getString("name"));
//                    c.setEmail(rs.getString("email"));
//                    c.setDepartment(rs.getString("department"));
//                    c.setStipend(rs.getDouble("stipend"));
//                }
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return c;
//    }



//    public static ResultSet getAllTrainees() throws SQLException, ClassNotFoundException {
//        Connection connection = DBConnection.getConnection();
//        String query = "SELECT * FROM Trainee";
//        PreparedStatement statement = connection.prepareStatement(query);
//        return statement.executeQuery();
//    }
//
//    public static ResultSet getTrainee(int id) throws SQLException, ClassNotFoundException {
//        Connection conn = DBConnection.getConnection();
//        String query = "SELECT * FROM Trainee WHERE id=?";
//        PreparedStatement ps = conn.prepareStatement(query);
//        ps.setInt(1,id);
//        return ps.executeQuery();
//    }

//    public static void updateTrainee(Trainee trainee, int id) throws SQLException, ClassNotFoundException {
//        Connection conn = DBConnection.getConnection();
//        String query = "UPDATE Trainee SET name=?, email=?, department=?, stipend=? WHERE id=?";
//        PreparedStatement ps = conn.prepareStatement(query);
//        ps.setInt(5,id);
//        ps.setString(1,trainee.getName());
//        ps.setString(2,trainee.getEmail());
//        ps.setString(3,trainee.getDepartment());
//        ps.setDouble(4,trainee.getStipend());
//
//        ps.execute();
//
//    }
//    public static void deleteTrainee(int id) throws SQLException, ClassNotFoundException {
//        Connection conn = DBConnection.getConnection();
//        String query = "DELETE FROM Trainee WHERE id=?";
//        PreparedStatement ps = conn.prepareStatement(query);
//        ps.setInt(1, id);
//
//        ps.execute();
//    }
}
