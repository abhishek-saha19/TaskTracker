import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

class TaskModel {
    private int id;
    private String description;
    private String status;
    private String createdAt;
    private String updatedAt;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    public void setStatus(String status) {
        this.status = status.toLowerCase();
    }

    public String getStatus() {
        return this.status;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedAt() {
        return this.createdAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUpdatedAt() {
        return this.updatedAt;
    }

    public void updateTime() {
        this.updatedAt = LocalDate.now().toString();
    }

    public TaskModel(int id, String description) {
        this.id = id;
        this.description = description;
        this.status = "todo";
        this.createdAt = LocalDate.now().toString();
        this.updatedAt = this.createdAt;
    }
}

class TaskActions {
    ArrayList<TaskModel> list = new ArrayList<>();
    FileHandler fHandler = new FileHandler();
    Gson gson = new Gson();

    private void loadData() {
        String task = fHandler.readFile();
        Type type = new TypeToken<ArrayList<TaskModel>>() {
        }.getType();

        list = gson.fromJson(task, type);

        if (list == null) {
            list = new ArrayList<>();
        }
    }

    public void addTask(String description) {
        loadData();
        int max = 0;
        for (TaskModel task : list) {
            if (task.getId() > max)
                max = task.getId();
        }

        int nextId = max + 1;
        TaskModel newTask = new TaskModel(nextId, description);
        list.add(newTask);

        String json = gson.toJson(list);
        fHandler.writeFile(json);
        System.out.println("Task added successfully (ID: " + nextId + ")");
    }

    public void listTasks(String status) {
        loadData();
        boolean found = false;

        if (list.isEmpty()) {
            System.out.println("No task found");
            return;
        }

        for (TaskModel task : list) {
            if (status == null || task.getStatus().equalsIgnoreCase(status)) {
                System.out.println("ID: "
                        + task.getId() + " | "
                        + task.getDescription() + " | "
                        + task.getStatus() + " | "
                        + task.getCreatedAt() + " | "
                        + task.getUpdatedAt());
                found = true;
            }
        }

        if (!found) {
            System.out.println("No task found for this filter");
        }
    }

    public void deleteTask(int id) {
        loadData();
        boolean found = false;

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId() == id) {
                list.remove(i);
                found = true;
                break;
            }
        }

        if (found) {
            String json = gson.toJson(list);
            fHandler.writeFile(json);
            System.out.println("Task removed with ID: " + id);
        } else {
            System.out.println("No task found for this ID");
        }
    }

    public void updateTask(int id, String description) {
        loadData();
        boolean found = false;

        for (TaskModel task : list) {
            if (task.getId() == id) {
                task.setDescription(description);
                task.updateTime();
                found = true;
                break;
            }
        }

        if (found) {
            String json = gson.toJson(list);
            fHandler.writeFile(json);
            System.out.println("Task updated with ID: " + id);
        } else {
            System.out.println("No task found with ID: " + id);
        }
    }

    public void statusUpdate(int id, String status) {
        if (!status.equalsIgnoreCase("todo") && !status.equalsIgnoreCase("done")
                && !status.equalsIgnoreCase("in-progress")) {
            System.out.println("Please choose status only between 'todo', 'done', or 'in-progress' ");
            return;
        }

        loadData();
        boolean found = false;

        for (TaskModel task : list) {
            if (task.getId() == id) {
                task.setStatus(status);
                task.updateTime();
                found = true;
                break;
            }

        }

        if (found) {
            String json = gson.toJson(list);
            fHandler.writeFile(json);
            System.out.println("Status updated successfully for ID: " + id);
        } else {
            System.out.println("No task found for ID: " + id);
        }
    }
}

class FileHandler {
    File file = new File("tasks.json");

    // checking if the file exists or not
    private void checkFileExists() {
        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            if (file.length() == 0) {
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write("[]");
                fileWriter.close();
            }
        } catch (IOException e) {
            System.out.println("File Handling Error");
            e.printStackTrace();
        }
    }

    // reading the file
    public String readFile() {
        checkFileExists();
        StringBuilder sb = new StringBuilder();
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }

            bufferedReader.close();
            fileReader.close();
        } catch (IOException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }

        String result = sb.toString();

        if (result.isEmpty())
            return "[]";
        else
            return result;
    }

    // writing in the file
    public void writeFile(String data) {
        try {
            FileWriter fileWriter = new FileWriter(file);

            fileWriter.write(data);

            fileWriter.close();
        } catch (IOException e) {
            System.out.println("File Handling error occured");
            e.printStackTrace();
        }
    }
}

class NewTaskTracker {
    public static void main(String[] args) {
        int n = args.length;
        if (n == 0) {
            System.out.println("No argument found please provide any argument");
            return;
        }

        String command = args[0];
        StringBuilder sb = new StringBuilder();

        for (int i = 1; i < n; i++) {
            sb.append(args[i]).append(" ");
        }

        String data = sb.toString().trim();

        TaskActions ta = new TaskActions();

        switch (command) {
            case "add":
                if (data.isEmpty()) {
                    System.out.println("Please provide task description");
                    return;
                } else {
                    ta.addTask(data);
                }
                break;

            case "delete":
                if (n >= 2) {
                    try {
                        int id = Integer.parseInt(args[1]);
                        ta.deleteTask(id);
                    } catch (Exception e) {
                        System.out.println("ID format is not valid please provide a number as ID");
                    }
                } else {
                    System.out.println("Please provide task ID");
                    return;
                }
                break;

            case "list":

                if (data.isEmpty()) {
                    ta.listTasks(null);
                } else {
                    ta.listTasks(data);
                }

                break;

            case "update":

                if (n >= 3) {

                    StringBuilder des = new StringBuilder();
                    for (int i = 2; i < n; i++) {
                        des.append(args[i]).append(" ");
                    }

                    String description = des.toString().trim();

                    try {
                        int id = Integer.parseInt(args[1]);
                        ta.updateTask(id, description);
                    } catch (Exception e) {
                        System.out.println("ID format is not valid please provide a number as ID");
                    }

                } else {
                    System.out.println("Please provide ID and description");
                    return;
                }
                break;

            case "mark":
                if (n >= 3) {

                    try {
                        int id = Integer.parseInt(args[1]);
                        ta.statusUpdate(id, args[2]);
                    } catch (Exception e) {
                        System.out.println("ID format is not valid please provide a number as ID");
                    }

                } else {
                    System.out.println("Please provide ID and status");
                    return;
                }
                break;

            default:
                System.out.println("Please choose valid command between add, delete, update, list, and mark");
                break;
        }
    }
}