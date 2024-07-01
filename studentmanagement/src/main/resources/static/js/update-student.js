document.addEventListener('DOMContentLoaded', function() {
    document.getElementById('updateStudentForm').addEventListener('submit', function(event) {
        event.preventDefault(); // Ngăn chặn hành động mặc định của form

        const studentId = document.getElementById('studentId').value;
        const formData = new FormData(this);
        const jsonData = JSON.stringify(Object.fromEntries(formData.entries()));

        fetch(`/api/admin/students/${studentId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: jsonData
        })
            .then(response => {
                if (response.ok) {
                    return response.json();
                } else {
                    return response.json().then(error => {
                        throw new Error(error.message || 'Failed to update student');
                    });
                }
            })
            .then(data => {
                alert('Student updated successfully');
                window.location.href = '/home-page'; // Chuyển hướng về trang home-page
            })
            .catch(error => {
                console.error('Error:', error);
                alert(`Error updating student: ${error.message}`);
            });
    });
});
