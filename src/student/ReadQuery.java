package student;

import ProjectGUI.ThirdWindow;

import javax.swing.*;
import java.sql.*;
import java.util.*;
import static ProjectGUI.ThirdWindow.*;

public class ReadQuery {
    //for DB connection
    static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/TeamProject?user=root";
    static final String USERNAME = "root";
    static final String PASSWORD = "0000";

    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    //1) Returns full list of students
    public String[] getStudentList(){
        String query = "Select Student_RegNo FROM Courses";
        String query_count = "SELECT COUNT(*) FROM courses;";

        String[] studentID = new String[1];

        try{
            conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            pst = conn.prepareStatement(query_count);
            pst.executeQuery();
            rs = pst.getResultSet();

            //Returns the count of total # of students
            rs.next();
            int length = rs.getInt("COUNT(*)");
            String[] copy = new String[length]; //Exclude 1st column - Student_RegNO
            studentID = Arrays.copyOf(copy, copy.length);

            //Returns studentID
            pst = conn.prepareStatement(query);
            pst.executeQuery();
            rs = pst.getResultSet();

            int i = 0;
            while(rs.next()){
                String id = rs.getString("Student_RegNo");
                studentID[i] = id;
                i++;
            }
        } catch(Exception e){
            e.printStackTrace();}
        return studentID;
    }


    //2) Returns the full list of modules
    public String[] getModuleList(){ //2 sec
        String[] moduleNames = new String[1];

        String query = "SELECT table_name FROM information_schema.tables where table_name LIKE 'CE%';";
        String query_count = "SELECT count(*) FROM information_schema.tables where table_name LIKE 'CE%';";

        try{
            conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            //Returns the count of total # of modules
            pst = conn.prepareStatement(query_count);
            pst.executeQuery();
            rs = pst.getResultSet();

            rs.next();
            int length = rs.getInt("count(*)");
            String[] copy = new String[length]; //Exclude 1st column - Student_RegNO
            moduleNames = Arrays.copyOf(copy, copy.length);

            //Returns module names
            pst = conn.prepareStatement(query);
            pst.executeQuery();
            rs = pst.getResultSet();

            int i = 0;
            while (rs.next()) {
                String s = rs.getString("TABLE_NAME");
                moduleNames[i] = s;
                i++;
            }
        }catch(Exception e){e.printStackTrace();}
        return moduleNames;
    }


    //3) Returns an integer array of count of grade distribution for selected MODULE
    //1st ind - first, 2nd ind - upper second, 3rd ind - lower second, 4th ind - third, 5th ind - fail
    public int[] getGradeDistribution(String module){
        String query = "SELECT * FROM " + module;

        //Counters
        int excellent_counter = 0;
        int good_counter = 0;
        int average_counter = 0;
        int poor_counter = 0;
        int fail_counter = 0;
        int student_total = 0;

        try{
            conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            pst = conn.prepareStatement(query);
            pst.executeQuery();
            rs = pst.getResultSet();

            while(rs.next()){
                int Grade = rs.getInt(module);
                student_total++;

                //Categorizing grades
                if (70<=Grade && Grade<=100){
                    excellent_counter++;
                }
                if (60<=Grade && Grade <=69){
                    good_counter++;
                }
                if (50<=Grade && Grade <=59){
                    average_counter++;
                }
                if (40<=Grade && Grade <=49){
                    poor_counter++;
                }
                if (Grade <40){
                    fail_counter++;
                }
            }
        } catch (Exception e){e.printStackTrace();}
        return new int[]{getPercentage(excellent_counter,student_total),getPercentage(good_counter,student_total),
                getPercentage(average_counter,student_total),getPercentage(poor_counter,student_total),
                getPercentage(fail_counter,student_total)}; //Returns an array of counters
    }

    //4) Private method for getGradeDistribution - Returns int percentage
    private int getPercentage(int dividend, int divisor) {
        double percentage = (double) dividend / divisor * 100;
        long percentage_double = Math.round(percentage);
        return Math.round(percentage_double);
    }

    //5) Get grade classification for selected STUDENT
    public int[] getStudentGradeDistribution(String student){
        HashMap<String, Integer> map = getStudentGrades(student); //Get all grades of selected student

        //Counters
        int excellent_counter = 0;
        int good_counter = 0;
        int average_counter = 0;
        int poor_counter = 0;
        int fail_counter = 0;

        for (Map.Entry<String, Integer> set : map.entrySet()) {
            int grade = set.getValue();
            //Categorizing grades
            if (grade < 40){
                fail_counter++;
            }
            else if (grade < 50){
                poor_counter++;
            }
            else if (grade < 60){
                average_counter++;
            }
            else if (grade < 70){
                good_counter++;
            }
            else {
                excellent_counter++;
            }
        }

        int[] return_array = {excellent_counter, good_counter, average_counter, good_counter,fail_counter};


        return return_array; //Returns an array of counters
    }

    //6) Returns Key-Student & Value-Grade for selected MODULE
    public HashMap getModuleStudentHM(String module){
        HashMap<String, Integer> map = new HashMap<>();
        try {
            conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            pst = conn.prepareStatement("SELECT * FROM " + module);
            pst.executeQuery();
            rs = pst.getResultSet();

            //Put values into hashmap
            while (rs.next()) {
                String student = rs.getString("Student_RegNo");
                int grade = rs.getInt(module);
                map.put(student, grade);
            }
        } catch (Exception e){e.printStackTrace();}
        return map;
    }

    //7) Returns average grade of selected STUDENT - Query of avg_grade SQL table
    public int getStudentAverage(String student){
        int grade = 0;
        String new_query = "SELECT grade_avg FROM AVG_Table WHERE Student_RegNo= " + student + ";";
        try{
            conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            pst = conn.prepareStatement(new_query);
            pst.executeQuery();
            rs = pst.getResultSet();

            while (rs.next()){
                grade = rs.getInt("grade_avg");
            }
        } catch (Exception e){e.printStackTrace();}
        return grade;
    }

    //8) Returns a sorted(DESCENDING) Key-student & value-average map of overall averages of student
    public HashMap<String, Integer> getStudentAveragesMap() {
        HashMap<String, Integer> map = new HashMap<>();
        String[] students = getStudentList(); //List of students

        for (String student: students){
            map.put(student,getStudentAverage(student));
        }
        HashMap<String, Integer> sortedMap = sortHMDescending(map); //sort by descending
        return sortedMap;
    }

    //9) Private method that returns HashMap sorted by values DESCENDING
    private HashMap<String, Integer> sortHMDescending(HashMap<String, Integer> hm){
        //Create a list from HashMap
        List<Map.Entry<String, Integer> > list =
                new LinkedList<Map.Entry<String, Integer> >(hm.entrySet());
        //Sorting the list
        Collections.sort(list, new Comparator<Map.Entry<String, Integer> >() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2){
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });
        Collections.reverse(list);

        //Put data from sorted list to hashmap
        HashMap<String, Integer> temp = new LinkedHashMap<>();

        for (Map.Entry<String, Integer> map : list) {
            String studentID = map.getKey();
            int grade = (Integer) map.getValue();
            temp.put(studentID, grade);
        }
        return temp;
    }

    //9) Returns a Sorted map of Key-module & Value-grade (for each module) for selected STUDENT
    public HashMap<String, Integer> getStudentGrades(String student){
        HashMap<String, Integer> map = new HashMap<>();
        String[] modules = getModuleList();

        for (String module: modules){
            String new_query = "SELECT " + module + "." + module + " from " + module
                    + " WHERE Student_RegNo = " + student + ";";
            try{
                conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                pst = conn.prepareStatement(new_query);
                pst.executeQuery();
                rs = pst.getResultSet();

                while (rs.next()){
                    int grade = rs.getInt(module);
                    map.put(module, grade);
                }
            } catch (Exception e){e.printStackTrace();}
        }
        HashMap<String, Integer> sortedMap = sortHMDescending(map); //sort
        return sortedMap;
    }

    //10)  Returns Best & Worst modules and their grades of selected STUDENT
    //1st ind - best module name, 2nd ind - best module grade, 3rd ind - worst module name, 4th ind - worst module grade
    public String[] bestWorstModule(String student){
        String[] bestWorst = new String[4];
        HashMap<String, Integer> map = getStudentGrades(student);
        ArrayList<String> modulesSorted = new ArrayList<>(map.keySet());
        bestWorst[0] = modulesSorted.get(0);
        bestWorst[1] = Integer.toString(map.get(bestWorst[0])); //the grade of best module
        bestWorst[2] = modulesSorted.get(modulesSorted.size()-1);
        bestWorst[3] = Integer.toString(map.get(bestWorst[2])); //the grade of worst module
        return bestWorst;
    }

    //11) Returns a hashmap of key-grade & value-rank
    public HashMap<Integer, Integer> getStudentRankHM(){
        String new_query = "SELECT grade_avg FROM AVG_Table;";
        ArrayList<Integer> avgArray = new ArrayList<>();

        try{
            conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            pst = conn.prepareStatement(new_query);
            pst.executeQuery();
            rs = pst.getResultSet();

            while (rs.next()){
                int grade = rs.getInt("grade_avg");
                avgArray.add(grade);
            }
        } catch (Exception e){
            e.printStackTrace();}
        Collections.sort(avgArray, Collections.reverseOrder()); //Descending order of averages
//        System.out.println(avgArray);

        //Create a hash map of key-grade & value-rank
        int cumulativeCount = 2;
        int sameGradeRank = 0;
        HashMap<Integer, Integer> rankMap = new HashMap<>();
        rankMap.put(avgArray.get(0), 1);

        for (int i = 1; i < avgArray.size(); i++){
            if (avgArray.get(i-1) != avgArray.get(i)){ //if grade is diff
                rankMap.put(avgArray.get(i), cumulativeCount);
                cumulativeCount++;
                sameGradeRank = cumulativeCount-1;
            }
            else{ //if same grade
                rankMap.put(avgArray.get(i), sameGradeRank);
                cumulativeCount++;
            }
        }
//        System.out.println(rankMap);
        return rankMap;
    }

    //12) Returns the average of selected MODULE
    public int getModuleAverage(String module) {
        String query = "SELECT * FROM " + module;
        double module_average = 0;
        int return_val = 0;
        int sum = 0;
        int count = 0; //student count

        try {
            conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            pst = conn.prepareStatement(query);
            pst.executeQuery();
            rs = pst.getResultSet();

            while(rs.next()){
                int grade = rs.getInt(module);
                sum += grade;
                count++;
            }
            module_average = sum/count;
            return_val = (int)(module_average);
        } catch (Exception e){e.printStackTrace();}
        return return_val;
    }


    //13) Sort HashMap by values ASCENDING
    public static HashMap<String, Integer> sortHMAscending(HashMap<String, Integer> hm)
    {
        //Create a list from HashMap
        List<Map.Entry<String, Integer> > list =
                new LinkedList<Map.Entry<String, Integer> >(hm.entrySet());

        //Sorting the list
        Collections.sort(list, new Comparator<Map.Entry<String, Integer> >() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2){
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        //Put data from sorted list to hashmap
        HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();

        for (Map.Entry<String, Integer> map : list) {
            String studentID = map.getKey();
            int grade = map.getValue();
            temp.put(studentID, grade);
        }
        return temp;
    }

    //14) Get all grades in selected module
    public int[] getAllGrades(String module){
        String query = "SELECT * FROM " + module;
        ArrayList<Integer> list = new ArrayList<Integer>();
        int[] arr = new int[1];

        try{
            //Establish connection
            conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            //Creating statement
            pst = conn.prepareStatement(query);
            //Execute query
            pst.executeQuery();
            //Process ResultSet object
            rs = pst.getResultSet();

            while(rs.next()){
                int grade = rs.getInt(module);
                list.add(grade);
            }

            Object[] obj = list.toArray();

            arr = Arrays.copyOf(arr, obj.length);
            int ind = 0;

            for (Object o : obj){
                int item = (Integer) o;
                arr[ind++] = item;
            }
        }
        catch(Exception e){e.printStackTrace();}

        return arr;
    }

    //15) Categorize the difficulty of module
    public String getDifficulty(String module){
        double module_total = 0;
        double percentage;
        int student_totalINT;
        int highG_counter = 0;
        int mediumG_counter = 0;
        int lowG_counter = 0;
        String Difficulty = null;

        String query = "SELECT * FROM " + module;

        try {
            conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            pst = conn.prepareStatement(query);
            pst.executeQuery();
            rs = pst.getResultSet();

            while(rs.next()){
                int Grade = rs.getInt(module);
                module_total++;

                if (Grade<40){
                    lowG_counter++;}
                if (40 <= Grade && Grade < 59){
                    mediumG_counter++;}
                if (60 <= Grade){
                    highG_counter++;
                }
            }
            String[] student_total = getStudentList();
            student_totalINT = student_total.length;

            percentage = (module_total/student_totalINT) * 100;

            if ((percentage < 50) &&
                    (lowG_counter > mediumG_counter) &&
                    (lowG_counter > highG_counter)) {
                Difficulty = "Hard";}
            if ((percentage >= 50 && percentage < 74) &&
                    (lowG_counter > mediumG_counter) &&
                    (lowG_counter > highG_counter)) {
                Difficulty = "Hard";}
            if ((percentage >= 75) &&
                    (lowG_counter > mediumG_counter) &&
                    (lowG_counter > highG_counter)) {
                Difficulty = "Hard";}

            if ((percentage < 50) &&
                    (mediumG_counter > lowG_counter) &&
                    (mediumG_counter > highG_counter)) {
                Difficulty = "Moderate";}
            if ((percentage >= 50 && percentage < 74) &&
                    (mediumG_counter > lowG_counter) &&
                    (mediumG_counter > highG_counter)) {
                Difficulty = "Moderate";}
            if ((percentage >= 75) &&
                    (mediumG_counter > lowG_counter) &&
                    (mediumG_counter > highG_counter)) {
                Difficulty = "Moderate";}

            if ((percentage < 50) &&
                    (highG_counter > mediumG_counter) &&
                    (highG_counter > lowG_counter)) {
                Difficulty = "Easy";}
            if ((percentage >= 50 && percentage < 74) &&
                    (highG_counter > mediumG_counter) &&
                    (highG_counter > lowG_counter)) {
                Difficulty = "Easy";}
            if ((percentage >= 75) &&
                    (highG_counter > mediumG_counter) &&
                    (highG_counter > lowG_counter)) {
                Difficulty = "Easy";}

        }catch (Exception e){e.printStackTrace();}
        return Difficulty;

    }

    //16) For user notes inserting into "Notes"
    public void insert() {
        String query = "INSERT INTO Teacher_Notes VALUES ('"+ notes.getText() +"')";

        try{
            //Establish connection
            conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            //Creating statement
            pst = conn.prepareStatement(query);
            //Execute query
//            pst.executeQuery();
            //Process ResultSet object
//            rs = pst.getResultSet();

            pst.executeUpdate(query);
            pst.close();
            conn.close();

            JOptionPane.showConfirmDialog(null, "Your Data Has been Inserted",
                    "Result", JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE);

        } catch(Exception e){
            e.printStackTrace();}
    }

    //Old method for getting selected student's average
//    public int getStudentAverage(String student){
//        String[] modules = getModuleList();
//        int count = 0; //count how many modules this student is taking
//        int sum = 0;
//        int average = 0;
//
//        for (String module: modules){
//            String new_query = "SELECT " + module + "." + module + " from " + module
//                    + " WHERE Student_RegNo = " + student + ";";
//            try{
//                conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
//                pst = conn.prepareStatement(new_query);
//                pst.executeQuery();
//                rs = pst.getResultSet();
//
//                while (rs.next()){
//                    int grade = rs.getInt(module);
//                    count += 1;
//                    sum += grade;
//                }
//            } catch (Exception e){e.printStackTrace();}
//        }
//        average = sum/count;
//        return average;
//    }

    public static void main(String[] args) {
        String student = "2500001";
        String module = "CE152_4_SP";
        String difficulty;
//        /* For Debugging
        ReadQuery r = new ReadQuery();
//        System.out.println(Arrays.toString(r.bestWorstModule(student)));
        System.out.println(r.getModuleAverage(module));

    }
}
