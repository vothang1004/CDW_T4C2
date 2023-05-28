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
    width: "70px",
  },
  {
    name: "Tên",
    selector: (row) => row.username,
    sortable: true,
    minWidth: "170px",
  },
  {
    name: "Điện thoại",
    selector: (row) => row.phoneNumber,
    sortable: true,
    width: "150px",
  },
  {
    name: "Email",
    selector: (row) => row.email,
    sortable: true,
    width: "200px",
    center: true
  },
  {
    name: "Giới tính",
    selector: (row) => row.gender,
    sortable: true,
    center: true
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
        `/admin/users?limit=${paginationOptions.rowPerPage}&page=${paginationOptions.page}`
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
