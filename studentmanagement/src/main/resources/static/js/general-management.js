document.addEventListener('DOMContentLoaded', function() {
    // Function to show the corresponding section
    function showSection(sectionId) {
        var sections = document.querySelectorAll('.management-section');
        sections.forEach(section => {
            section.classList.remove('active');
        });
        document.getElementById(sectionId).classList.add('active');
    }

    // Event listeners for sidebar links
    document.getElementById('generalManagementLink').addEventListener('click', function() {
        showSection('generalManagement');
        loadGeneralInfo();
    });
    document.getElementById('studentManagementLink').addEventListener('click', function() {
        showSection('studentManagement');
    });
    document.getElementById('courseManagementLink').addEventListener('click', function() {
        showSection('courseManagement');
    });

    // Function to load general information
    function loadGeneralInfo() {
        fetch('/api/admin/students/count')
            .then(response => response.json())
            .then(data => {
                console.log('Student count:', data);
                document.getElementById('studentCount').textContent = data;
            })
            .catch(error => console.error('Error fetching student count:', error));

        fetch('/api/admin/courses/count')
            .then(response => response.json())
            .then(data => {
                document.getElementById('courseCount').textContent = data;
            })
            .catch(error => console.error('Error fetching course count:', error));
    }

    // Load general info by default
    showSection('generalManagement');
    loadGeneralInfo();
});
