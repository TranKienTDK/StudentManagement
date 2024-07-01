document.addEventListener('DOMContentLoaded', function() {
    var studentId = document.getElementById('studentId').value;

    function attachEventListeners() {
        document.querySelectorAll('.register-btn').forEach(button => {
            button.addEventListener('click', function() {
                var courseElement = this.closest('tr');
                var courseId = courseElement.getAttribute('data-course-id');
                var courseName = courseElement.getAttribute('data-course-name');
                var courseDescription = courseElement.getAttribute('data-course-description');
                var credits = courseElement.getAttribute('data-credits');
                registerCourse(courseId, studentId, courseName, courseDescription, credits);
            });
        });

        document.querySelectorAll('.unregister-btn').forEach(button => {
            button.addEventListener('click', function() {
                var courseElement = this.closest('tr');
                var courseId = courseElement.getAttribute('data-course-id');
                var courseName = courseElement.getAttribute('data-course-name');
                var courseDescription = courseElement.getAttribute('data-course-description');
                var credits = courseElement.getAttribute('data-credits');
                unregisterCourse(courseId, studentId, courseName, courseDescription, credits);
            });
        });
    }

    attachEventListeners();

    function registerCourse(courseId, studentId, courseName, courseDescription, credits) {
        console.log("Registering course: " + courseId);
        fetch('/api/registration/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            credentials: 'include',
            body: JSON.stringify({ courseId: parseInt(courseId), studentId: parseInt(studentId) })
        })
            .then(response => {
                if (!response.ok) {
                    return response.json().then(err => { throw new Error(err.message) });
                }
                return response.json();
            })
            .then(data => {
                if (data.status === 'success') {
                    // Nếu đăng ký thành công, xóa khóa học khỏi danh sách khóa học có sẵn và thêm vào danh sách khóa học đã đăng ký
                    removeCourseFromAvailable(courseId);
                    addCourseToRegistered(courseId, courseName, courseDescription, credits);
                } else if (data.status === 'error' && data.message === 'Course already registered.') {
                    alert("Course already registered.");
                } else {
                    alert("Error: " + data.message);
                }
            })
            .catch(error => {
                alert("Error registering course: " + error.message);
            });
    }

    function unregisterCourse(courseId, studentId, courseName, courseDescription, credits) {
        console.log("Unregistering course: " + courseId);
        fetch('/api/registration/unregister', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            credentials: 'include',
            body: JSON.stringify({ courseId: parseInt(courseId), studentId: parseInt(studentId) })
        })
            .then(response => {
                if (!response.ok) {
                    return response.json().then(err => { throw new Error(err.message) });
                }
                return response.json();
            })
            .then(data => {
                if (data.status === 'success') {
                    // Nếu hủy đăng ký thành công, xóa khóa học khỏi danh sách khóa học đã đăng ký
                    removeCourseFromRegistered(courseId);
                } else {
                    alert("Error: " + data.message);
                }
            })
            .catch(error => {
                alert("Error unregistering course: " + error.message);
            });
    }

    function removeCourseFromAvailable(courseId) {
        var courseElement = document.getElementById("course-" + courseId);
        if (courseElement) {
            courseElement.remove();
        }
    }

    function removeCourseFromRegistered(courseId) {
        var courseElement = document.getElementById("registered-course-" + courseId);
        if (courseElement) {
            courseElement.remove();
        }
    }

    function addCourseToRegistered(courseId, courseName, courseDescription, credits) {
        var tbody = document.getElementById("registeredCoursesTableBody");
        var newRow = document.createElement("tr");
        newRow.id = "registered-course-" + courseId;
        newRow.setAttribute('data-course-id', courseId);
        newRow.setAttribute('data-course-name', courseName);
        newRow.setAttribute('data-course-description', courseDescription);
        newRow.setAttribute('data-credits', credits);
        newRow.innerHTML = `
            <td>${courseId}</td>
            <td>${courseName}</td>
            <td>${courseDescription}</td>
            <td>${credits}</td>
            <td><button class="unregister-btn">Unregister</button></td>
        `;
        tbody.appendChild(newRow);
        newRow.querySelector('.unregister-btn').addEventListener('click', function() {
            var courseElement = this.closest('tr');
            var courseId = courseElement.getAttribute('data-course-id');
            var courseName = courseElement.getAttribute('data-course-name');
            var courseDescription = courseElement.getAttribute('data-course-description');
            var credits = courseElement.getAttribute('data-credits');
            unregisterCourse(courseId, studentId, courseName, courseDescription, credits);
        });
    }

    // Search functionality
    var searchInput = document.getElementById('courseSearchInput');
    searchInput.addEventListener('input', function() {
        var filter = searchInput.value.toLowerCase();
        var rows = document.querySelectorAll('#availableCoursesTableBody tr');

        rows.forEach(row => {
            var courseName = row.getAttribute('data-course-name').toLowerCase();
            if (courseName.includes(filter)) {
                row.style.display = '';
            } else {
                row.style.display = 'none';
            }
        });
    });
});
