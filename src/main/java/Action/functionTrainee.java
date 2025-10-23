package Action;

import DAO.TraineeRepo;
import Model.Trainee;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/trainee/*")
public class functionTrainee extends HttpServlet {
    private final Gson gson = new Gson();
    private final TraineeRepo traineeRepo = new TraineeRepo();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        try (BufferedReader reader = request.getReader()) {
            Trainee trainee = gson.fromJson(reader, Trainee.class);
            boolean isSaved = traineeRepo.saveTrainee(trainee);

            if (isSaved) {
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("{\"message\":\"Trainee saved successfully\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("{\"error\":\"Failed to insert trainee\"}");
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"Failed to save the data\"}");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        String idParam = request.getParameter("id");
        String pathInfo = request.getPathInfo();

        int id = 0;
        if (idParam != null && !idParam.isEmpty()) {
            id = Integer.parseInt(idParam);
        } else if (pathInfo != null && pathInfo.length() > 1) {
            id = Integer.parseInt(pathInfo.substring(1));
        }

        if (id == 0) {
            // get all trainees
            List<Trainee> list = traineeRepo.getAllTrainee();
            response.getWriter().write(gson.toJson(list));
        } else {
            // get by id
            Trainee trainee = traineeRepo.getTraineeById(id);
            if (trainee != null) {
                response.getWriter().write(gson.toJson(trainee));
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"error\":\"Trainee not found\"}");
            }
        }
    }

 //   @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        response.setContentType("application/json");
//
//        String pathInfo = request.getPathInfo();
//
//        if (pathInfo == null || pathInfo.equals("/")) {
//
//            List<Trainee> list = traineeRepo.getAllTrainee();
//            response.getWriter().write(gson.toJson(list));
//        } else {
//
//            try {
//                int id = Integer.parseInt(pathInfo.substring(2));
//                Trainee trainee = traineeRepo.getTraineeById(id);
//
//                if (trainee != null) {
//                    response.getWriter().write(gson.toJson(trainee));
//                } else {
//                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
//                    response.getWriter().write("{\"error\":\"Trainee not found\"}");
//                }
//            } catch (NumberFormatException e) {
//                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//                response.getWriter().write("{\"error\":\"Invalid ID format\"}");
//            }
//        }
//    }



    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        try (BufferedReader reader = request.getReader()) {
            Trainee trainee = gson.fromJson(reader, Trainee.class);

            // If JSON doesn’t include ID, try query/path
            if (trainee.getId() == 0) {
                String idParam = request.getParameter("id");
                String pathInfo = request.getPathInfo();
                if (idParam != null) trainee.setId(Integer.parseInt(idParam));
                else if (pathInfo != null && pathInfo.length() > 1)
                    trainee.setId(Integer.parseInt(pathInfo.substring(1)));
            }

            if (trainee.getId() == 0) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\":\"Missing trainee ID\"}");
                return;
            }

            boolean isUpdated = traineeRepo.updateTrainee(trainee);
            if (isUpdated) {
                response.getWriter().write("{\"message\":\"Trainee updated successfully\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"error\":\"Trainee not found\"}");
            }

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"Invalid JSON: " + e.getMessage() + "\"}");
        }
    }

//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        response.setContentType("application/json");
//
//        String pathInfo = request.getPathInfo();
//        if (pathInfo == null || pathInfo.equals("/")) {
//
//            List<Trainee> list = traineeRepo.getAllTrainee();
//            response.getWriter().write(gson.toJson(list));
//        } else {
//
//            try {
//                int id = Integer.parseInt(pathInfo.substring(1));
//                Trainee trainee = traineeRepo.getTraineeById(id);
//
//                if (trainee != null) {
//                    response.getWriter().write(gson.toJson(trainee));
//                } else {
//                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
//                    response.getWriter().write("{\"error\":\"Trainee not found\"}");
//                }
//            } catch (NumberFormatException e) {
//                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//                response.getWriter().write("{\"error\":\"Invalid ID format\"}");
//            }
//        }
//    }

//    @Override
//    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        response.setContentType("application/json");
//
//        try (BufferedReader reader = request.getReader()) {
//            Trainee trainee = gson.fromJson(reader, Trainee.class);
//
//            if (trainee.getId() == 0) {
//                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//                response.getWriter().write("{\"error\":\"Missing trainee ID\"}");
//                return;
//            }
//
//            boolean isUpdated = traineeRepo.updateTrainee(trainee);
//            if (isUpdated) {
//                response.getWriter().write("{\"message\":\"Trainee updated successfully\"}");
//            } else {
//                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
//                response.getWriter().write("{\"error\":\"Trainee not found\"}");
//            }
//
//        } catch (Exception e) {
//            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//            response.getWriter().write("{\"error\":\"Invalid JSON: " + e.getMessage() + "\"}");
//        }
//    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        String idParam = request.getParameter("id");
        if (idParam == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"ID is required for delete\"}");
            return;
        }

        try {
            int id = Integer.parseInt(idParam);
            boolean isDeleted = traineeRepo.deleteTrainee(id);

            if (isDeleted) {
                response.getWriter().write("{\"message\":\"Trainee deleted successfully\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"error\":\"Trainee not found\"}");
            }

        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"Invalid ID format\"}");
        }
    }


//    @Override
//    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        response.setContentType("application/json");
//        String pathInfo = request.getPathInfo();
//
//        if (pathInfo == null || pathInfo.equals("/")) {
//            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//            response.getWriter().write("{\"error\":\"ID is required for delete\"}");
//            return;
//        }
//
//        try {
//            int id = Integer.parseInt(pathInfo.substring(1));
//            boolean isDeleted = traineeRepo.deleteTrainee(id);
//
//            if (isDeleted) {
//                response.getWriter().write("{\"message\":\"Trainee deleted successfully\"}");
//            } else {
//                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
//                response.getWriter().write("{\"error\":\"Trainee not found\"}");
//            }
//
//        } catch (NumberFormatException e) {
//            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//            response.getWriter().write("{\"error\":\"Invalid ID format\"}");
//        }
//    }
}

//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
//
//    Gson gson = new Gson();
//    Trainee trainee = gson.fromJson(req.getReader(), Trainee.class);
//        res.setContentType("application/json");
//        try {
//            TraineeRepo.saveTrainee(trainee);
//            System.out.println("success");
//           // res.getWriter().println("success");
//        } catch (SQLException | ClassNotFoundException e) {
//            e.printStackTrace();
//
//            JSONObject error = new JSONObject();
//            error.put("status", "error");
//            error.put("message", e.getMessage());
//            res.getWriter().print(error.toString());
//            res.getWriter().flush();
//        }
//    }
//
//
//    @Override
//    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        response.setContentType("application/json");
//        String pathInfo = request.getPathInfo();
//
//        if (pathInfo == null || pathInfo.equals("/")) {
//            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//            response.getWriter().write("{\"error\":\"ID is required for delete\"}");
//            return;
//        }
//
//        try {
//            int id = Integer.parseInt(pathInfo.substring(1));
//            boolean isDeleted = traineeRepo.deleteTrainee(id);
//
//            if (isDeleted) {
//                response.getWriter().write("{\"message\":\"Trainee deleted successfully\"}");
//            } else {
//                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
//                response.getWriter().write("{\"error\":\"Trainee not found\"}");
//            }
//
//        } catch (NumberFormatException | SQLException | ClassNotFoundException e) {
//            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//            response.getWriter().write("{\"error\":\"Invalid ID format\"}");
//        }
//    }
//
//
//
//    @Override
//    protected void doDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
//        int id = Integer.parseInt(req.getParameter("id"));
//
//        try {
//            TraineeRepo.deleteTrainee(id);
//            res.getWriter().println("Successfully Deleted");
//        } catch (SQLException | ClassNotFoundException e) {
//            e.printStackTrace();
//
//            JSONObject error = new JSONObject();
//            error.put("status", "error");
//            error.put("message", e.getMessage());
//            res.getWriter().print(error.toString());
//            res.getWriter().flush();
//        }
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String idParam = req.getParameter("id");
//        resp.setContentType("application/json");
//
//        if (idParam != null) {
//            int id = Integer.parseInt(idParam);
//
//            try {
//                ResultSet rs = TraineeRepo.getTrainee(id);
//                JSONObject trainee = null;
//
//                while (rs.next()) {
//                    trainee = new JSONObject();
//                    trainee.put("id", rs.getInt("id"));
//                    trainee.put("name", rs.getString("name"));
//                    trainee.put("email", rs.getString("email"));
//                    trainee.put("department", rs.getString("department"));
//                    trainee.put("stipend", rs.getDouble("stipend"));
//                }
//
//                JSONObject responseObj = new JSONObject();
//                if (trainee == null) {
//                    responseObj.put("status", "NOT FOUND");
//                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
//                } else {
//                    responseObj.put("status", "success");
//                    responseObj.put("data", trainee);
//                }
//
//                resp.getWriter().print(responseObj.toString());
//                resp.getWriter().flush();
//
//            } catch (SQLException | ClassNotFoundException e) {
//                e.printStackTrace();
//                JSONObject error = new JSONObject();
//                error.put("status", "error");
//                error.put("message", e.getMessage());
//                resp.getWriter().print(error.toString());
//                resp.getWriter().flush();
//            }
//
//        } else {  //  This ‘else’ runs when there’s no ID (fetch all trainees)
//            try {
//                ResultSet rs = TraineeRepo.getAllTrainees();
//                JSONArray traineesArray = new JSONArray();
//
//                while (rs.next()) {
//                    JSONObject trainee = new JSONObject();
//                    trainee.put("id", rs.getInt("id"));
//                    trainee.put("name", rs.getString("name"));
//                    trainee.put("email", rs.getString("email"));
//                    trainee.put("department", rs.getString("department"));
//                    trainee.put("stipend", rs.getDouble("stipend"));
//
//                    traineesArray.put(trainee);
//                }
//
//                JSONObject responseObj = new JSONObject();
//                responseObj.put("status", "success");
//                responseObj.put("data", traineesArray);
//
//                resp.getWriter().print(responseObj.toString());
//                resp.getWriter().flush();
//
//            } catch (SQLException | ClassNotFoundException e) {
//                e.printStackTrace();
//                JSONObject error = new JSONObject();
//                error.put("status", "error");
//                error.put("message", e.getMessage());
//                resp.getWriter().print(error.toString());
//                resp.getWriter().flush();
//            }
//        }
//    }
//
//    @Override
//    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        int id = Integer.parseInt(req.getParameter("id"));
//
//        Gson gson = new Gson();
//        Trainee trainee = gson.fromJson(req.getReader(), Trainee.class);
//
//        try {
//            TraineeRepo.updateTrainee(trainee, id);
//            resp.getWriter().println("Success");
//        } catch (SQLException | ClassNotFoundException e) {
//            e.printStackTrace();
//
//            JSONObject error = new JSONObject();
//            error.put("status", "error");
//            error.put("message", e.getMessage());
//            resp.getWriter().print(error.toString());
//            resp.getWriter().flush();
//        }
//    }
//}
