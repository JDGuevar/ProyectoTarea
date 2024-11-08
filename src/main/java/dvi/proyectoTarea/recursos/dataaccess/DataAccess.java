package dvi.proyectoTarea.recursos.dataaccess;

import at.favre.lib.crypto.bcrypt.BCrypt;
import dvi.proyectoTarea.recursos.objetos.Exercici;
import dvi.proyectoTarea.recursos.objetos.Intent;
import dvi.proyectoTarea.recursos.objetos.Review;
import dvi.proyectoTarea.recursos.objetos.Usuari;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Miguel
 */
public class DataAccess {

    private Connection getConnection() {
        Connection connection = null;
        Properties properties = new Properties();
        try {
            properties.load(new FileReader(new File("src/main/java/dvi/proyectoTarea/recursos/propiedades/application.properties")));
        } catch (Exception e) {
            System.out.println("No existe archivo properties");
            e.printStackTrace();
        }

        String coString = properties.getProperty("conexion");
        try {
            connection = DriverManager.getConnection(coString);
        } catch (SQLException ex) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
        }

        return connection;
    }

    public Usuari getUser(int id) {
        Usuari user = null;
        String sql = "SELECT * FROM Usuaris WHERE Id = ?";
        try (Connection connection = getConnection(); PreparedStatement selectStatement = connection.prepareStatement(sql);) {
            selectStatement.setInt(1, id);
            ResultSet resultSet = selectStatement.executeQuery();
            user = new Usuari();
            while (resultSet.next()) {
                user.setId(resultSet.getInt("Id"));
                user.setNom(resultSet.getString("Nom"));
                user.setEmail(resultSet.getString("Email"));
                user.setPasswordHash(resultSet.getString("PasswordHash"));
                user.setInstructor(resultSet.getBoolean("isInstructor"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public Exercici getExercici(int id) {
        Exercici exercici = null;
        String sql = "SELECT * FROM Exercicis WHERE Id = ?";
        try (Connection connection = getConnection(); PreparedStatement selectStatement = connection.prepareStatement(sql);) {
            selectStatement.setInt(1, id);
            ResultSet resultSet = selectStatement.executeQuery();
            if (resultSet.next()) {
                exercici = new Exercici();
                exercici.setId(resultSet.getInt("Id"));
                exercici.setNomExercici(resultSet.getString("NomExercici"));
                exercici.setDescripció(resultSet.getString("Descripcio"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exercici;
    }

    public int tryLogin(String user, char[] pass) {
        try {
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Usuaris WHERE Email = ?");
            stmt.setString(1, user);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String storedPassword = rs.getString("PasswordHash");
                var resultado = BCrypt.verifyer().verify(pass, storedPassword);
                if (resultado.verified) {
                    if (rs.getBoolean("isInstructor")) {
                        return 1; //log instructor
                    }
                } else {
                    return 2; //No log password invalida
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 3;//No log no usuario
    }

    public ArrayList<Usuari> getAllUsers() {
        ArrayList<Usuari> usuaris = new ArrayList<>();
        String sql = "SELECT * FROM Usuaris WHERE isInstructor=0";
        try (Connection connection = getConnection(); PreparedStatement selectStatement = connection.prepareStatement(sql);) {

            ResultSet resultSet = selectStatement.executeQuery();

            while (resultSet.next()) {
                Usuari user = new Usuari();
                user.setId(resultSet.getInt("Id"));
                user.setNom(resultSet.getString("Nom"));
                user.setEmail(resultSet.getString("Email"));
                user.setPasswordHash(resultSet.getString("PasswordHash"));
                user.setInstructor(resultSet.getBoolean("isInstructor"));
                usuaris.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuaris;
    }

    public int registerUser(Usuari u) {
        String sql = "INSERT INTO dbo.Usuaris (Nom, Email, PasswordHash, isInstructor)"
                + " VALUES (?,?,?,?)"
                + " SELECT CAST(SCOPE_IDENTITY() as int)";
        try (Connection conn = getConnection(); PreparedStatement insertStatement = conn.prepareStatement(sql)) {
            insertStatement.setString(1, u.getNom());
            insertStatement.setString(2, u.getEmail());
            insertStatement.setString(3, u.getPasswordHash());
            insertStatement.setBoolean(4, u.isInstructor());

            int newUserId = insertStatement.executeUpdate();
            return newUserId;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public ArrayList<Intent> getAttemptsPendingReview() {
        ArrayList<Intent> intents = new ArrayList<>();
        String sql = "SELECT Intents.Id, Intents.IdUsuari, Usuaris.Nom,"
                + " Intents.IdExercici, Exercicis.NomExercici, Timestamp_Inici,"
                + " Timestamp_Fi, VideoFile"
                + " FROM Intents INNER JOIN Usuaris ON Intents.IdUsuari=Usuaris.Id"
                + " INNER JOIN Exercicis ON Intents.IdExercici=Exercicis.Id"
                + " WHERE Intents.Id NOT IN"
                + " (SELECT IdIntent FROM Review)"
                + " ORDER BY Timestamp_Inici";
        try (Connection connection = getConnection(); PreparedStatement selectStatement = connection.prepareStatement(sql);) {

            ResultSet resultSet = selectStatement.executeQuery();

            while (resultSet.next()) {
                Intent attempt = new Intent();
                attempt.setId(resultSet.getInt("Id"));
                attempt.setIdUsuari(resultSet.getInt("IdUsuari"));
                attempt.setNomUsuari(resultSet.getString("Nom"));
                attempt.setIdExercici(resultSet.getInt("IdExercici"));
                attempt.setNomExercici(resultSet.getString("NomExercici"));
                attempt.setTimestamp_Inici(resultSet.getString("Timestamp_Inici"));
                attempt.setTimestamp_Fi(resultSet.getString("Timestamp_Fi"));
                attempt.setVideofile(resultSet.getString("VideoFile"));
                intents.add(attempt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return intents;
    }
    
    public ArrayList<Intent> getAttempts() {
        ArrayList<Intent> intents = new ArrayList<>();
        String sql = "SELECT Intents.Id, Intents.IdUsuari, Usuaris.Nom,"
                + " Intents.IdExercici, Exercicis.NomExercici, Timestamp_Inici,"
                + " Timestamp_Fi, VideoFile"
                + " FROM Intents INNER JOIN Usuaris ON Intents.IdUsuari=Usuaris.Id"
                + " INNER JOIN Exercicis ON Intents.IdExercici=Exercicis.Id"
                + " ORDER BY Timestamp_Inici";
        try (Connection connection = getConnection(); PreparedStatement selectStatement = connection.prepareStatement(sql);) {

            ResultSet resultSet = selectStatement.executeQuery();

            while (resultSet.next()) {
                Intent attempt = new Intent();
                attempt.setId(resultSet.getInt("Id"));
                attempt.setIdUsuari(resultSet.getInt("IdUsuari"));
                attempt.setNomUsuari(resultSet.getString("Nom"));
                attempt.setIdExercici(resultSet.getInt("IdExercici"));
                attempt.setNomExercici(resultSet.getString("NomExercici"));
                attempt.setTimestamp_Inici(resultSet.getString("Timestamp_Inici"));
                attempt.setTimestamp_Fi(resultSet.getString("Timestamp_Fi"));
                attempt.setVideofile(resultSet.getString("VideoFile"));
                intents.add(attempt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return intents;
    }

    public int insertReview(Review r) {
        int result = 0;
        String sql = "INSERT INTO dbo.Review (IdIntent, IdReviewer, Valoracio, Comentari)"
                + " VALUES (?,?,?,?)";
        try (Connection conn = getConnection(); PreparedStatement insertStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            insertStatement.setInt(1, r.getIdIntent());
            insertStatement.setInt(2, r.getIdReviewer());
            insertStatement.setInt(3, r.getValoracio());
            insertStatement.setString(4, r.getComentari());

            result = insertStatement.executeUpdate();
            if (result == 0) {
                throw new SQLException("Creating review failed, no rows affected.");
            }

            try (ResultSet generatedKeys = insertStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    Long longResult = generatedKeys.getLong(1);
                    result = longResult.intValue();
                } else {
                    throw new SQLException("Creating review failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Mètode per comprovar si un intent es la repetició de un exercici
     * 'failed'. Comprova si ja existeix un intent amb el mateix IdUsuari i
     * IdExercici i la \n data es anterior a la de intent.
     *
     * @param intent El intent a comprovar
     * @return el id del intent anterior o 0 si no existeix un intent anterior.
     */
    public int getPreviousFailedAttempt(Intent intent) {
        return 0;
    }

    public ArrayList<Intent> getAttemptsPerUser(int userId) {
        ArrayList<Intent> intents = new ArrayList<>();
        String sql = "SELECT Intents.Id, Intents.IdUsuari, Usuaris.Nom,"
                + " Intents.IdExercici, Exercicis.NomExercici, Timestamp_Inici,"
                + " Timestamp_Fi, VideoFile"
                + " FROM Intents INNER JOIN Usuaris ON Intents.IdUsuari=Usuaris.Id"
                + " INNER JOIN Exercicis ON Intents.IdExercici=Exercicis.Id"
                + " WHERE Intents.IdUsuari=?"
                + " ORDER BY Intents.IdExercici";
        try (Connection connection = getConnection(); PreparedStatement selectStatement = connection.prepareStatement(sql);) {
            selectStatement.setInt(1, userId);
            ResultSet resultSet = selectStatement.executeQuery();

            while (resultSet.next()) {
                Intent attempt = new Intent();
                attempt.setId(resultSet.getInt("Id"));
                attempt.setIdUsuari(resultSet.getInt("IdUsuari"));
                attempt.setNomUsuari(resultSet.getString("Nom"));
                attempt.setIdExercici(resultSet.getInt("IdExercici"));
                attempt.setNomExercici(resultSet.getString("NomExercici"));
                attempt.setTimestamp_Inici(resultSet.getString("Timestamp_Inici"));
                attempt.setTimestamp_Fi(resultSet.getString("Timestamp_Fi"));
                attempt.setVideofile(resultSet.getString("VideoFile"));
                intents.add(attempt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return intents;

    }
    
    public Intent getAttemptFullData(int id) {
        Intent intent = new Intent();
        String sql = "SELECT Intents.Id, Intents.IdUsuari, Usuaris.Nom,"
                + " Intents.IdExercici, Exercicis.NomExercici, Timestamp_Inici,"
                + " Timestamp_Fi, VideoFile"
                + " FROM Intents INNER JOIN Usuaris ON Intents.IdUsuari=Usuaris.Id"
                + " INNER JOIN Exercicis ON Intents.IdExercici=Exercicis.Id"
                + " WHERE Intents.Id=?"
                + " ORDER BY Intents.IdExercici";
        try (Connection connection = getConnection(); PreparedStatement selectStatement = connection.prepareStatement(sql);) {
            selectStatement.setInt(1, id);
            ResultSet resultSet = selectStatement.executeQuery();

            while (resultSet.next()) {
                intent.setId(resultSet.getInt("Id"));
                intent.setIdUsuari(resultSet.getInt("IdUsuari"));
                intent.setNomUsuari(resultSet.getString("Nom"));
                intent.setIdExercici(resultSet.getInt("IdExercici"));
                intent.setNomExercici(resultSet.getString("NomExercici"));
                intent.setTimestamp_Inici(resultSet.getString("Timestamp_Inici"));
                intent.setTimestamp_Fi(resultSet.getString("Timestamp_Fi"));
                intent.setVideofile(resultSet.getString("VideoFile"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return intent;

    }

    public Review getAttemptReview(int idIntent) {
        Review review = null;
        String sql = "SELECT * FROM Review WHERE IdIntent = ?";
        try (Connection connection = getConnection(); PreparedStatement selectStatement = connection.prepareStatement(sql);) {
            selectStatement.setInt(1, idIntent);
            ResultSet resultSet = selectStatement.executeQuery();
            review = new Review();
            while (resultSet.next()) {
                review.setId(resultSet.getInt("Id"));
                review.setIdIntent(resultSet.getInt("IdIntent"));
                review.setIdReviewer(resultSet.getInt("IdReviewer"));
                review.setValoracio(resultSet.getInt("Valoracio"));
                review.setComentari(resultSet.getString("Comentari"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return review;
    }

    public int updateReview(Review r) {
        int result = 0;
        String sql = "UPDATE Review SET Valoracio=?, Comentari=? WHERE Id=?";
        try (Connection conn = getConnection(); PreparedStatement updateStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            updateStatement.setInt(3, r.getId());
            updateStatement.setInt(1, r.getValoracio());
            updateStatement.setString(2, r.getComentari());

            result = updateStatement.executeUpdate();
            if (result == 0) {
                throw new SQLException("Updating review failed, no rows affected.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<Object[]> getUserData() {
        ArrayList<Object[]> userData = new ArrayList<>();
        ArrayList<Usuari> users = getAllUsers();
        for (Usuari user : users) {
            userData.add(new Object[]{user.getNom(), user.getId()});
        }
        return userData;
    }

    public List<Object[]> getIntentData(ArrayList<Intent> i) {
        ArrayList<Object[]> IntentData = new ArrayList<>();
        ArrayList<Intent> Intents = i;
        String state = "";
        
        for (Intent intent : Intents) {
            Review r = getAttemptReview(intent.getId());
            
            if (r.getValoracio() < 3) {
                state = "Suspenso";
            } if (r.getValoracio() >= 3 ) {
                state = "Aprovado";
            }
            if (r.getValoracio() == 0 ) {
                state = "Pendiente";
            }
            Exercici e = getExercici(intent.getIdExercici());
            IntentData.add(new Object[]{intent.getId(),intent.getIdUsuari(), intent.getNomExercici(), intent.getTimestamp_Inici(), state});
        }
        return IntentData;
    }
}
