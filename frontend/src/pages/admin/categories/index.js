import React, { useEffect, useState } from "react";
import AdminLayout from "../../../components/layouts/admin/AdminLayout";
import { Paper, Typography } from "@mui/material";
import PrivateRoute from "../../../components/route/PrivateRoute";
import AdminRequiredRoute from "../../../components/route/AdminRequiredRoute";
import TableBase from "../../../components/table/TableBase";
import useAxiosPrivate from "../../../hooks/useAxiosPrivate";

const columns = [
  {
    name: "ID",
    selector: (row) => row.id,
    sortable: true,
    width: "150px",
  },
  {
    name: "Tên",
    selector: (row) => row.name,
    sortable: true,
    minWidth: "170px",
  },
  {
    name: "Mô tả",
    selector: (row) => row.description,
    sortable: true,
  },
];

function AdminUser() {
  const axiosPrivate = useAxiosPrivate();
  const [products, setProducts] = useState([]);
  const [paginationOptions, setPaginationOptions] = useState({
    totalRows: 0,
    page: 1,
    rowPerPage: 20,
  });

  // get product
  const getProduct = async () => {
    try {
      const resp = await axiosPrivate.get(
        `/categories?limit=${paginationOptions.rowPerPage}&page=${paginationOptions.page}`
      );
      if (resp && resp.status === 200) {
        setProducts(resp.data);
        setPaginationOptions({
          ...paginationOptions,
          totalRows: resp.data.totalElements || resp.data.length,
        });
      }
    } catch (error) {
      console.log({ error });
    }
  };

  useEffect(() => {
    getProduct();
  }, [paginationOptions.page, paginationOptions.rowPerPage]);

  return (
    <PrivateRoute>
      <AdminRequiredRoute>
        <AdminLayout>
          <Paper sx={{ height: "100%", padding: "10px" }}>
            <Typography sx={{ paddingBottom: "10px" }}>
              Danh sách người dùng
            </Typography>
            <TableBase
              data={products}
              columns={columns}
              pagination
              paginationPerPage={paginationOptions.rowPerPage}
              paginationTotalRows={paginationOptions.totalRows}
              onChangePage={(newPage) => {
                setPaginationOptions({ ...paginationOptions, page: newPage });
              }}
              onChangeRowsPerPage={(value) =>
                setPaginationOptions({
                  ...paginationOptions,
                  rowPerPage: value,
                })
              }
            />
          </Paper>
        </AdminLayout>
      </AdminRequiredRoute>
    </PrivateRoute>
  );
}

export default AdminUser;
