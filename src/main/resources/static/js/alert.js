Swal.fire({
    title: "Custom animation with Animate.css",
    showClass: {
        popup: `
      animate__animated
      animate__fadeInUp
      animate__faster
    `
    },
    hideClass: {
        popup: `
      animate__animated
      animate__fadeOutDown
      animate__faster
    `
    }
});