
function formatDateForSubmit() {
    const inputDate = document.getElementById('dateOfBirth').value;
    const formattedDate = moment(inputDate, 'YYYY-MM-DD').format('YYYY-MM-DD');
    document.getElementById('dateOfBirth').value = formattedDate;
}


function addStudent() {
    formatDateForSubmit();

    const fullName = document.getElementById('fullName').value;
    const userName = document.getElementById('userName').value;
    const password = document.getElementById('password').value;
    const role = document.getElementById('role').value;
    const dateOfBirth = document.getElementById('dateOfBirth').value;
    const email = document.getElementById('email').value;
    const phoneNumber = document.getElementById('phoneNumber').value;
    const address = document.getElementById('address').value;

    const student = {
        account: {
            userName,
            password,
            role
        },
        fullName,
        dateOfBirth,
        email,
        phoneNumber,
        address
    };

    fetch('/api/students', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(student)
    })
    .then(response => response.json())
    .then(data => {
        var studentTableBody = document.getElementById('studentTableBody');
        var row = document.createElement('tr');
        row.innerHTML = `<td>${data.studentId}</td>
                         <td>${data.fullName}</td>
                         <td>${data.dateOfBirth}</td>
                         <td>${data.email}</td>
                         <td>${data.phoneNumber}</td>
                         <td>${data.address}</td>`;
        studentTableBody.appendChild(row);
        // Reset the form
        document.getElementById('studentForm').reset();
        document.getElementById('add-student-form').style.display = 'none';
    })
    .catch(error => console.error('Error adding student:', error));
}
