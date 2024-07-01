function addCourse() {

    const courseName = document.getElementById("courseName");
    const courseDescription = document.getElementById("courseDescription");
    const credits = document.getElementById("credits");

    const course = {
        courseName,
        courseDescription,
        credits
    };

    fetch('/api/admin/courses', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(course)
    })
        .then(response => response.json())
        .then(data => {
            var courseTableBody = document.getElementById("courseTableBody");
            var row = document.createElement('tr');
            row.innerHTML = `<td>${data.courseId}</td>
                             <td>${data.courseName}</td>
                             <td>${data.courseDescription}</td>
                             <td>${data.credits}</td>`;
            courseTableBody.appendChild(row);
            // Reset the form
            document.getElementById('courseForm').reset();
            document.getElementById('add-course-form').style.display = 'none';
        })
        .catch(error => console.error('Error adding course:', error));
}