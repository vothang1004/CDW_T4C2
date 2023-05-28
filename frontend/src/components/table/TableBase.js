import React, { useState } from "react";
import DataTable from "react-data-table-component";
import { useTheme } from "@mui/material/styles";
import { Box, CircularProgress, Typography } from "@mui/material";

function TableBase({
  columns,
  data,
  pagination,
  paginationTotalRows,
  paginationPerPage,
  onChangePage = () => {},
  onChangeRowsPerPage = () => {},
  title,
  progressPending = false,
  onRowClicked,
}) {
  const theme = useTheme();

  return (
    <DataTable
      persistTableHead
      fixedHeader
      fixedHeaderScrollHeight="380px"
      striped
      pointerOnHover
      highlightOnHover
      onRowClicked={onRowClicked}
      //   onRowMouseEnter={handleRowHover}
      //   onRowMouseLeave={handleRowHoverEnd}
      columns={columns}
      data={data}
      // pagination start
      pagination={pagination}
      paginationServer
      paginationTotalRows={paginationTotalRows}
      paginationPerPage={paginationPerPage}
      paginationDefaultPage={1}
      paginationRowsPerPageOptions={[20, 50, 100, 500]}
      paginationComponentOptions={{
        rowsPerPageText: "Dòng trên bảng",
        rangeSeparatorText: "trên",
      }}
      onChangePage={onChangePage}
      onChangeRowsPerPage={onChangeRowsPerPage}
      // pagination end
      // process start
      progressPending={progressPending}
      progressComponent={
        <Box
          sx={{
            width: "100%",
            height: "100px",
            paddingTop: "20px",
            textAlign: "center",
          }}
        >
          <CircularProgress />
        </Box>
      }
      // process end
      noDataComponent={
        <Typography
          sx={{ fontSize: "13px", textAlign: "center", padding: "20px 0" }}
        >
          {title ? `Không có ${title}` : "Không có dữ liệu"}
        </Typography>
      }
      customStyles={{
        headCells: {
          style: {
            backgroundColor: theme.palette.primary.main,
            color: "#fff",
          },
        },
      }}
    />
  );
}

export default TableBase;
