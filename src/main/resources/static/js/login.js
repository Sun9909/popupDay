document.addEventListener('DOMContentLoaded', function() {
    let flashMessageMeta = document.querySelector('meta[name="flash-message"]');
    let flashTypeMeta = document.querySelector('meta[name="flash-type"]');

    if (flashMessageMeta && flashTypeMeta) {
        let flashMessage = flashMessageMeta.content;
        let flashType = flashTypeMeta.content;

        if (flashMessage && flashType) {
            Swal.fire({
                icon: flashType,
                title: flashMessage,
                showConfirmButton: true
            });
        }
    }
});