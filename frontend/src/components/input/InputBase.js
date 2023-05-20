import React from "react";

function InputBase({
  register = () => {},
  name,
  type = "text",
  label,
  required,
  placeholder,
  errorMessage,
}) {
  return (
    <div className="flex flex-col gap-[5px]">
      {label && (
        <label className="text-[12px]">
          {label} {required && <span className="text-red text-[14px]">*</span>}
        </label>
      )}
      <input
        className="w-full py-2 px-4 text-[14px] border border-gray focus:border-black rounded-md
        outline-none placeholder:text-[12px]"
        type={type}
        placeholder={placeholder}
        {...register(name)}
      />
      {errorMessage && (
        <p className="text-[12px] text-red italic">{errorMessage}</p>
      )}
    </div>
  );
}

export default InputBase;
