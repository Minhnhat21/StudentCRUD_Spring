//Get
$(document).ready( function () {
    getAll();
    addStudent();
    $("#editModal").validate({
        rules: {
            name: {
                required: true,
                minlength: 3
            },
            email: {
                required: true,
                email: true
            }
        },
        messages : {
            name: {
                minlength: "Ten nen co it nhat 3 ky tu"
            },
            email: {
                email: "The email should be in the format: abc@domain.tld"
            }
        }
    });
    $("#addContent").validate({
        rules: {
            name: {
                required: true,
                minlength: 3
            },
            email: {
                required: true,
                email: true
            }
        },
        messages : {
            name: {
                minlength: "Ten nen co it nhat 3 ky tu"
            },
            email: {
                email: "The email should be in the format: abc@domain.tld"
            }
        }
    });
});
function getAll() {
    let myURL =	'http://localhost:8080/api/v1/students';
    $.ajax({
        type: "GET",
        url: myURL,
        dataType: 'json',

        success: function(data){
            console.log(data)
            let str = '';
            $.each(data, function (i, item) {
                str += "<tr id='row"+item.id+"'>";
                str += "<td>" + item.id + "</td>";
                str += "<td>" + item.name + "</td>";
                str += "<td>" + item.email + "</td>";
                str += "<td>" + item.dob + "</td>";
                str += "<td><a onclick='editStudent("+item.id+")' href=\"#editStudentModal\" id='editStudentBtn' class=\"edit\" data-toggle=\"modal\"><i class=\"material-icons\" data-toggle=\"tooltip\" title=\"Edit\">&#xE254;</i></a></td>";
                str += "<td><a onclick='deleteStudent("+item.id+")' class=\"delete\" data-toggle=\"modal\"><i class=\"material-icons\" data-toggle=\"tooltip\" title=\"Delete\">&#xE872;</i></a></td>";
                str += "</tr>";

            });
            $('#get-data').html(str);
        }
    });
    return false;
};
//getStudent
function getStudentById() {
    let currentID =  $("#edit-id").val();
    $.ajax({
        type: "GET",
        url: 'http://localhost:8080/api/v1/students/' + currentID,
        dataType: 'json',
        success: function(data){
            console.log(data);
            $("#edit-name").val(data.name);
            $("#edit-email").val(data.email);
            $("#edit-dob").val(data.dob);
        }
    });
    return false;
}
//Add
function addStudent() {
    let myURL =	'http://localhost:8080/api/v1/students';
    let student = {};
    $('#add-data-btn').on('click', function (e) {
        if($('#addContent').valid()) {
            student.name = $('#name').val();
            student.email = $('#email').val();
            student.dob = $('#dob').val();
            let studentJson= JSON.stringify(student);
            $.ajax({
                type: "POST",
                url: myURL,
                data: studentJson,
                contentType: "application/json; charset=utf-8",
                success: function(data) {
                    getAll();
                },
                error: function () {
                    alert("Loi roi ban oi");
                }
            });
        }
    });
    return false;
}
// Delete
function deleteStudent(id) {
    $.ajax({
        type: "DELETE",
        url: 'http://localhost:8080/api/v1/students/' + id,
        success: function() {
            $('#row'+id).html("");
        },
        error: function () {
            alert("Loi roi ban oi");
        }
    });
};
function deleteAllStudent() {
        $.ajax({
            type: "DELETE",
            url: 'http://localhost:8080/api/v1/students/delete',
            success: function() {
                alert("Delete Success");
            },
            error: function () {
                alert("Loi roi ban oi");
            }
        });
}

function editStudent(id) {
    let currentID = id;
    console.log(currentID);
    $("#edit-id").val(currentID);
    getStudentById();
    $('#save-btn').on('click', function (e) {
        if($('#editModal').valid()) {
            let updateStudent = {};
            updateStudent.name = $('#edit-name').val();
            updateStudent.email = $('#edit-email').val();
            updateStudent.dob = $('#edit-dob').val();
            let updateStudentJson= JSON.stringify(updateStudent);
            $.ajax({
                type: "PUT",
                url: 'http://localhost:8080/api/v1/students/' + id,
                data: updateStudentJson,
                contentType: "application/json; charset=utf-8",

                success: function () {
                    alert("Update Student Successfull");
                    $('#name-search').val("");
                },
                error: function (error) {
                    alert(error);
                }
            })
        }

    });
}

function searchStudentByName() {
    $.ajax({
            type: "GET",
            url: 'http://localhost:8080/api/v1/students/q?name=' + $('#name-search').val(),
            dataType: 'json',

            success: function(data) {
                console.log(data)
                let str = '';
                $.each(data, function (i, item) {
                    str += "<tr id='row" + item.id + "'>";
                    str += "<td>" + item.id + "</td>";
                    str += "<td>" + item.name + "</td>";
                    str += "<td>" + item.email + "</td>";
                    str += "<td>" + item.dob + "</td>";
                    str += "<td><a onclick='editStudent(" + item.id + ")' href=\"#editStudentModal\" id='editStudentBtn' class=\"edit\" data-toggle=\"modal\"><i class=\"material-icons\" data-toggle=\"tooltip\" title=\"Edit\">&#xE254;</i></a></td>";
                    str += "<td><a onclick='deleteStudent(" + item.id + ")' class=\"delete\" data-toggle=\"modal\"><i class=\"material-icons\" data-toggle=\"tooltip\" title=\"Delete\">&#xE872;</i></a></td>";
                    str += "</tr>";

                });
                $('#get-data').html(str);
            }
    });

}


