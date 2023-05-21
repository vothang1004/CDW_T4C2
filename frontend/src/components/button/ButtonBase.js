import React from "react";

function ButtonBase({
  type,
  classes = "",
  fullWidth,
  loading = false,
  hieght = "42px",
  children,
}) {
  return (
    <button
      type={type}
      className={`px-4 py-2 h-[${hieght}] text-white bg-black hover:bg-red transition-all duration-200 rounded-md
      flex items-center justify-center ${
        fullWidth ? "w-full" : "w-fit"
      } ${classes} ${loading ? "bg-gray pointer-events-none" : ""}`}
    >
      {loading ? (
        <div className="w-5 h-5 bg-transparent border-2 border-l-transparent border-white rounded-full animate-spin"></div>
      ) : (
        children
      )}
    </button>
  );
}

export default ButtonBase;