function uploadImage() {
    const imageInput = document.getElementById('imageInput');
    const uploadedImage = document.getElementById('uploadedImage');
    const imageContainer = document.getElementById('imageContainer');

    if (imageInput.files && imageInput.files[0]) {
        const file = imageInput.files[0];
        const reader = new FileReader();

        reader.onload = function(e) {
            const imageData = e.target.result;
            localStorage.setItem('uploadedImage', imageData);
            uploadedImage.src = imageData;
            imageContainer.style.display = 'block';
        };

        reader.readAsDataURL(file);
    } else {
        imageContainer.style.display = 'none';
    }
}

// Load the uploaded image on page load
window.onload = function() {
    const uploadedImage = document.getElementById('uploadedImage');
    const imageContainer = document.getElementById('imageContainer');
    const storedImageData = localStorage.getItem('uploadedImage');

    if (storedImageData) {
        uploadedImage.src = storedImageData;
        imageContainer.style.display = 'block';
    } else {
        imageContainer.style.display = 'none';
    }
};