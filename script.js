function smoothScrollTo(targetPosition, duration) {
  const startPosition = window.pageYOffset;
  const distance = targetPosition - startPosition;
  const startTime = performance.now();

  function scrollStep(timestamp) {
    const elapsed = timestamp - startTime;
    const progress = Math.min(elapsed / duration, 1);
    window.scrollTo(0, startPosition + distance * progress);

    if (progress < 1) {
      requestAnimationFrame(scrollStep);
    }
  }

  requestAnimationFrame(scrollStep);
}
function hideOnScroll(id, opacity) {
  const element = document.getElementById(id);

  if (!element) {
    console.error(`Element with ID "${id}" not found.`);
    return;
  }

  window.addEventListener("scroll", () => {
    const scrollPosition = window.scrollY || document.documentElement.scrollTop;
    const viewportHeight = window.innerHeight;

    if (scrollPosition >= viewportHeight) {
      element.style.opacity = "0";
    } else {
      element.style.opacity = opacity;
    }
  });
}

hideOnScroll("page1-home", "1");
hideOnScroll("image-wrapper-screen-game", "0.3");

var helloNavbar = document.getElementById("hello-navbar");
var theGameNavbar = document.getElementById("thegame-navbar");
var aboutNavbar = document.getElementById("about-navbar");
var contactUsNavbar = document.getElementById("contactus-navbar");

var theGame = document.getElementById("page2-thegame");
var about = document.getElementById("page3-about");
var contactUs = document.getElementById("page4-contact-us");

theGameNavbar.addEventListener("click", function () {
  smoothScrollTo(theGame.offsetTop + window.innerHeight, 500);
});

aboutNavbar.addEventListener("click", function () {
  smoothScrollTo(about.offsetTop + window.innerHeight, 1000);
});

contactUsNavbar.addEventListener("click", function () {
  smoothScrollTo(contactUs.offsetTop + window.innerHeight, 1000);
});

var phoneNavbar = document.getElementById("phone-navbar");
var phoneNavbarText = document.getElementById("text-wrapper-phone-navbar");
phoneNavbar.addEventListener("click", function () {
  if (phoneNavbarText.style.display === "block") {
    phoneNavbarText.style.display = "none";
    phoneNavbar.style.backgroundColor = "transparent";
  } else {
    phoneNavbarText.style.display = "block";
    phoneNavbar.style.backgroundColor = "#d9d9d9";
  }
});

var phonehelloNavbar = document.getElementById("phone-hello-navbar");
var phonetheGameNavbar = document.getElementById("phone-thegame-navbar");
var phoneaboutNavbar = document.getElementById("phone-about-navbar");
var phonecontactUsNavbar = document.getElementById("phone-contactus-navbar");

phonehelloNavbar.addEventListener("click", function () {
  smoothScrollTo(0, 500);
});
phonetheGameNavbar.addEventListener("click", function () {
  smoothScrollTo(theGame.offsetTop + window.innerHeight, 500);
});

phoneaboutNavbar.addEventListener("click", function () {
  smoothScrollTo(about.offsetTop + window.innerHeight, 1000);
});

phonecontactUsNavbar.addEventListener("click", function () {
  smoothScrollTo(contactUs.offsetTop + window.innerHeight, 1000);
});
