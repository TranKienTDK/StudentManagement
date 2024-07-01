document.addEventListener('DOMContentLoaded', function() {
    // Gọi REST API để lấy dữ liệu học phần
    fetch('/api/admin/courses')
        .then(response => response.json())
        .then(data => {
            // Điền dữ liệu vào bảng
            var courseTableBody = document.getElementById('courseTableBody');
            data.forEach(course => {
                var row = document.createElement('tr');
                row.innerHTML = `<td>${course.courseId}</td>
                                 <td><a href="/courses/${course.courseId}/students" class="course-link">${course.courseName}</a></td>
                                 <td>${course.courseDescription}</td>
                                 <td>${course.credits}</td>
                                 <td>
                                    <button class="btn btn-sm btn-warning" onclick="window.location.href='/update-course/${course.courseId}'">Update</button>
                                    <button class="btn btn-sm btn-danger" onclick="deleteCourse(${course.courseId})">Delete</button>
                                 </td>`;
                courseTableBody.appendChild(row);
            });
        })
        .catch(error => console.error('Error fetching courses:', error));

    // Thêm sự kiện tìm kiếm
    var courseSearchInput = document.getElementById('courseSearchInput');
    courseSearchInput.addEventListener('keyup', function() {
        var filter = courseSearchInput.value.toLowerCase();
        var rows = document.querySelectorAll('#courseTableBody tr');
        rows.forEach(row => {
            var courseName = row.children[1].textContent.toLowerCase();
            if (courseName.includes(filter)) {
                row.style.display = '';
            } else {
                row.style.display = 'none';
            }
        });
    });
});

// Function to delete a course
function deleteCourse(courseId) {
    if (confirm("Are you sure you want to delete this course?")) {
        fetch(`/api/admin/courses/${courseId}`, {
            method: 'DELETE',
        })
            .then(response => {
                if (response.ok) {
                    alert('Course deleted successfully');
                    location.reload(); // Reload the page to update the table
                } else {
                    alert('Error deleting course');
                }
            })
            .catch(error => console.error('Error deleting course:', error));
    }
}
