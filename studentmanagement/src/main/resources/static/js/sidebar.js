document.addEventListener('DOMContentLoaded', function() {
    const generalManagementLink = document.getElementById('generalManagementLink');
    const studentManagementLink = document.getElementById('studentManagementLink');
    const courseManagementLink = document.getElementById('courseManagementLink');
    const generalManagementSection = document.getElementById('generalManagement');
    const studentManagementSection = document.getElementById('studentManagement');
    const courseManagementSection = document.getElementById('courseManagement');
    const sidebarLinks = document.querySelectorAll('.sidebar a');

    // Xử lý khi nhấp vào link quản lý chung
    generalManagementLink.addEventListener('click', function(event) {
        event.preventDefault(); // Ngăn chặn hành vi mặc định của thẻ 'a'
        generalManagementSection.style.display = 'block';
        studentManagementSection.style.display = 'none';
        courseManagementSection.style.display = 'none';
        setActiveLink(this); // Gọi hàm để set active link
    });

    // Xử lý khi nhấp vào link quản lý sinh viên
    studentManagementLink.addEventListener('click', function(event) {
        event.preventDefault(); // Ngăn chặn hành vi mặc định của thẻ 'a'
        generalManagementSection.style.display = 'none';
        studentManagementSection.style.display = 'block';
        courseManagementSection.style.display = 'none';
        setActiveLink(this); // Gọi hàm để set active link
    });

    // Xử lý khi nhấp vào link quản lý học phần
    courseManagementLink.addEventListener('click', function(event) {
        event.preventDefault(); // Ngăn chặn hành vi mặc định của thẻ 'a'
        generalManagementSection.style.display = 'none';
        studentManagementSection.style.display = 'none';
        courseManagementSection.style.display = 'block';
        setActiveLink(this); // Gọi hàm để set active link
    });

    // Hàm để set active link
    function setActiveLink(activeLink) {
        // Xóa class 'active' ở tất cả các links
        sidebarLinks.forEach(link => {
            link.classList.remove('active');
        });
        // Thêm class 'active' vào link được nhấp
        activeLink.classList.add('active');
    }

    // Set the default active section: general management
    generalManagementSection.style.display = 'block';
    studentManagementSection.style.display = 'none';
    courseManagementSection.style.display = 'none';
    setActiveLink(generalManagementLink); // Set link quản lý chung là active mặc định
});
