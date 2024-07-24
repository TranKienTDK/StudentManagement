document.addEventListener('DOMContentLoaded', function() {
    // Gọi REST API để lấy dữ liệu sinh viên
    fetch('/api/admin/students')
        .then(response => response.json())
        .then(data => {
            // Lưu dữ liệu sinh viên vào biến toàn cục để sử dụng lại
            window.studentsData = data;
            // Điền dữ liệu vào bảng
            displayStudents(data); // Hiển thị dữ liệu lần đầu
        })
        .catch(error => console.error('Error fetching students:', error));

    // Thêm sự kiện cho ô nhập liệu tìm kiếm
    document.getElementById('searchInput').addEventListener('input', searchStudents);
});

// Function to display students in table
function displayStudents(data) {
    var studentTableBody = document.getElementById('studentTableBody');
    studentTableBody.innerHTML = ''; // Xóa dữ liệu cũ trong bảng
    data.forEach(student => {
        var row = document.createElement('tr');
        var dateOfBirth = new Date(student.dateOfBirth).toISOString().slice(0, 10);
        row.innerHTML = `<td>${student.studentId}</td>
                         <td><a href="/students/${student.studentId}/courses">${student.fullName}</a></td>
                         <td>${dateOfBirth}</td>
                         <td>${student.email}</td>
                         <td>${student.phoneNumber}</td>
                         <td>${student.address}</td>
                         <td>
                            <button class="btn btn-sm btn-warning" onclick="window.location.href='/update-student/${student.studentId}'">Update</button>
                            <button class="btn btn-sm btn-danger" onclick="deleteStudent(${student.studentId})">Delete</button>
                         </td>`;
        studentTableBody.appendChild(row);
    });
}

// Function to delete a student
function deleteStudent(studentId) {
    if (confirm("Are you sure you want to delete this student?")) {
        fetch(`/api/admin/students/${studentId}`, {
            method: 'DELETE',
        })
            .then(response => {
                if (response.ok) {
                    alert('Student deleted successfully');
                    location.reload(); // Reload the page to update the table
                } else {
                    alert('Error deleting student');
                }
            })
            .catch(error => console.error('Error deleting student:', error));
    }
}

// Function to search for students
function searchStudents() {
    var input, filter, filteredData;
    input = document.getElementById("searchInput");
    filter = input.value.toUpperCase();

    // Lọc dữ liệu sinh viên theo từ khóa tìm kiếm
    filteredData = window.studentsData.filter(student => {
        return student.fullName.toUpperCase().includes(filter) || student.studentId.toString().includes(filter);
    });

    // Hiển thị dữ liệu sinh viên đã lọc
    displayStudents(filteredData);
}
