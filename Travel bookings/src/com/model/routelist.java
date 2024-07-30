package com.model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import com.DataBaseUtil;

public class routelist {
	
	static List<Route> routes = new ArrayList<>();
	
	public static void listOfRoutes() throws SQLException {
		routes.add(new Route(1, "Nellore", "Hyderabad", LocalDate.parse("2024-08-10", DateTimeFormatter.ISO_LOCAL_DATE), 1000, 40));
		//add more routes here
		insertRoutes(routes);
	}
	
	
	public static void insertRoutes(List<Route> routes) throws SQLException {
        Connection conn = DataBaseUtil.getConnection();
        PreparedStatement pstmtInsert = null;
        PreparedStatement pstmtCheck = null;
        ResultSet rs = null;

        String sqlInsert = "INSERT INTO Routes (route_id, source, destination, journey_date, ticket_price_per_passenger, no_of_seats_available) VALUES (?, ?, ?, ?, ?, ?)";
        String sqlCheck = "SELECT COUNT(*) FROM Routes WHERE route_id = ?";

        try {
            pstmtInsert = conn.prepareStatement(sqlInsert);
            pstmtCheck = conn.prepareStatement(sqlCheck);

            for (Route route : routes) {
                pstmtCheck.setInt(1, route.getRouteId());
                rs = pstmtCheck.executeQuery();
                rs.next();
                int count = rs.getInt(1);

                if (count == 0) { // Route does not exist
                    pstmtInsert.setInt(1, route.getRouteId());
                    pstmtInsert.setString(2, route.getSource());
                    pstmtInsert.setString(3, route.getDestination());
                    pstmtInsert.setDate(4, java.sql.Date.valueOf(route.getJourneyDate()));
                    pstmtInsert.setDouble(5, route.getTicketPricePerPassenger());
                    pstmtInsert.setInt(6, route.getNoOfSeatsAvailable());
                    pstmtInsert.addBatch(); // Add the insert statement to the batch
                }
            }

            // Execute the batch
            pstmtInsert.executeBatch();

        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (rs != null) rs.close();
                if (pstmtInsert != null) pstmtInsert.close();
                if (pstmtCheck != null) pstmtCheck.close();
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
}