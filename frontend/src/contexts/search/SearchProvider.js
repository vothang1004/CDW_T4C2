import React, { useContext, createContext, useState } from "react";
import { useRouter } from "next/router";

const SearchContext = createContext();

function SearchProvider({ children }) {
  const router = useRouter();
  const [searchText, setSearchText] = useState(router.query.search || "");
  return (
    <SearchContext.Provider value={[searchText, setSearchText]}>
      {children}
    </SearchContext.Provider>
  );
}

export default SearchProvider;

export const useSearchContext = () => {
  const value = useContext(SearchContext);
  if (!value) {
    throw new Error("Search context must be used inside search provider");
  }
  return value;
};
