import { useRouter } from "next/router";
import React, { useEffect, useRef, useState } from "react";
import { FiSearch } from "react-icons/fi";
import { useSearchContext } from "../../../contexts/search/SearchProvider";

function SearchBar() {
  const router = useRouter();
  const [searchText, setSearchText] = useSearchContext();
  const [searchSelf, setSearchSefl] = useState(searchText || "");
  const timerRef = useRef();

  const handleSearch = (e) => {
    if (e.key === "Enter") {
      router.push({
        pathname: "/products",
        query: {
          search: searchText,
        },
      });
    }
  };
  useEffect(() => {
    timerRef.current = setTimeout(() => {
      setSearchText(searchSelf);
    }, 500);
    return () => clearTimeout(timerRef.current);
  }, [searchSelf]);

  return (
    <div className="flex h-[40px] w-[410px] items-center bg-white rounded-md pr-2">
      <input
        className="bg-transparent flex-grow h-full border-none outline-none px-2 text-black text-sm"
        type="text"
        placeholder="Tìm sản phẩm..."
        value={searchSelf}
        onChange={(e) => setSearchSefl(e.target.value)}
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
