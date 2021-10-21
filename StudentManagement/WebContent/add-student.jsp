<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
 <title>Student Management</title>
            <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        </head>

        <body>

            <header>
                <nav class="navbar navbar-expand-md navbar-dark" style="background-color: tomato">
                    <div>
                        <a href="https://www.javaguides.net" class="navbar-brand"> Student Management </a>
                    </div>

                    <ul class="navbar-nav">
                        <li><a href="<%=request.getContextPath()%>/list" class="nav-link">Students</a></li>
                    </ul>
                </nav>
            </header>
            <br>
            <div class="container col-md-5">
                <div class="card">
                    <div class="card-body">
                        <c:if test="${student != null}">
                            <form action="update" method="post"> </form>
                        </c:if>
                        <c:if test="${student == null}">
                            <form action="insert" method="post"> </form>
                        </c:if>

                      
                            <h2>
                                <c:if test="${student != null}">
                                    Edit Student
                                </c:if>
                                <c:if test="${student == null}">
                                    Add New Student
                                </c:if>
                            </h2>
                       

                        <c:if test="${student != null}">
                            <input type="hidden" name="id" value="<c:out value='${student_no}' />" />
                        </c:if>

                        <fieldset class="form-group">
                            <label>Student Name</label> <input type="text" value="<c:out value='${student_name}' />" class="form-control" name="student_name" required="required">
                        </fieldset>

                        <fieldset class="form-group">
                            <label>Student DOB</label> <input type="date" value="<c:out value='${student.dob}' />" class="form-control" name="dob">
                        </fieldset>

                        <fieldset class="form-group">
                            <label>Student DOJ</label> <input type="date" value="<c:out value='${student.doj}' />" class="form-control" name="doj">
                        </fieldset>

                        <button type="submit" class="btn btn-success">Save</button>
                       
                    </div>
                </div>
            </div>
        </body>
</html>