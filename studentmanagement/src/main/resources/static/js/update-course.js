document.addEventListener('DOMContentLoaded', function () {
    document.getElementById('updateCourseForm').addEventListener('submit', function (event) {
        event.preventDefault();

        const courseId = document.getElementById('courseId').value;
        const formData = new FormData(this);
        const jsonData = JSON.stringify(Object.fromEntries(formData.entries()));

        fetch(`/api/admin/courses/${courseId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: jsonData
        })
            .then(response => {
                if(response.ok) {
                    return response.json();
                }
                else {
                    return response.json().then(error => {
                        throw new Error(error.message || 'Failed to update course');
                    })
                }
            })
            .then(data => {
                alert('Course updated successfully');
                window.location.href = '/home-page';
        })
            .catch(error => {
                console.error('Error', error);
                alert(`Error updating course: ${error.message}`);
            });
    });
});