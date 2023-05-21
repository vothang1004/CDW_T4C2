import React, { useState } from "react";
import { FiSearch } from "react-icons/fi";
import { useSelector } from "react-redux";

function SearchBar({ onSearch = () => {}, defaultSearchText = "" }) {
  const [searchText, setSearchText] = useState(defaultSearchText || "");

  const handleSearch = (e) => {
    if (e.key === "Enter") {
      onSearch(searchText);
    }
  };

  return (
    <div className="flex h-[40px] w-[410px] items-center bg-white rounded-md pr-2">
      <input
        className="bg-transparent flex-grow h-full border-none outline-none px-2 text-black text-sm"
        type="text"
        placeholder="Tìm sản phẩm..."
        value={searchText}
        onChange={(e) => setSearchText(e.target.value)}
        onKeyUp={handleSearch}
      />
      <span
        onClick={() => handleSearch({ key: "Enter" })}
        className="text-black inline-flex w-[30px] h-[30px] hover:bg-gray cursor-pointer
          items-center justify-center transition-all rounded-full"
      >
        <FiSearch size="18px" />
      </span>
    </div>
  );
}

export default SearchBar;
