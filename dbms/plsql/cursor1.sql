DECLARE
  -- Step 1: Declare the cursor and a variable to hold each row
  CURSOR student_cursor IS
    SELECT name, grade FROM students;
    
  v_name   students.name%TYPE;
  v_grade  students.grade%TYPE;

BEGIN
  -- Step 2: Open the cursor (runs the query, loads rows into memory)
  OPEN student_cursor;
  
  -- Step 3: Fetch rows one by one in a loop
  LOOP
    FETCH student_cursor INTO v_name, v_grade;
    EXIT WHEN student_cursor%NOTFOUND;  -- stop when no more rows
    
    DBMS_OUTPUT.PUT_LINE(v_name || ' - ' || v_grade);
  END LOOP;
  
  -- Step 4: Close the cursor
  CLOSE student_cursor;
END;