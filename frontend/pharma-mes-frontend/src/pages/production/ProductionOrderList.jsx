import { useEffect, useMemo, useState } from "react";
import { useNavigate } from "react-router-dom";

import {
    Alert,
    Box,
    Button,
    CircularProgress,
    FormControl,
    InputLabel,
    MenuItem,
    Paper,
    Select,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    TextField,
    Typography
} from "@mui/material";

import MainLayout from "../../layouts/MainLayout";

import {
    getAllProductionOrders
} from "../../services/productionService";

function ProductionOrderList() {

    const navigate = useNavigate();

    const [productionOrders, setProductionOrders] = useState([]);

    const [loading, setLoading] = useState(true);

    const [error, setError] = useState("");

    const [search, setSearch] = useState("");

    const [statusFilter, setStatusFilter] = useState("ALL");

    useEffect(() => {

        const loadProductionOrders = async () => {

            try {

                setLoading(true);
                setError("");

                const data =
                    await getAllProductionOrders();

                console.log(
                    "Production orders loaded:",
                    data
                );

                setProductionOrders(data);

            } catch (error) {

                console.error(
                    "Failed to load production orders:",
                    error.response?.data ||
                    error.message
                );

                setError(
                    "Unable to load production order data."
                );

            } finally {

                setLoading(false);

            }
        };

        loadProductionOrders();

    }, []);

    /*
     * Get unique statuses from the backend response.
     *
     * This follows the same approach used by BatchList.jsx
     * instead of hard-coding the ProductionStatus enum values.
     */
    const statuses = useMemo(() => {

        const uniqueStatuses =
            productionOrders
                .map(
                    (productionOrder) =>
                        productionOrder.status
                )
                .filter(Boolean);

        return [...new Set(uniqueStatuses)];

    }, [productionOrders]);

    /*
     * Search by:
     * - Production Order ID
     * - Order Number
     * - Batch Number
     * - Material ID
     */
    const filteredProductionOrders = useMemo(() => {

        const searchValue =
            search.trim().toLowerCase();

        return productionOrders.filter(
            (productionOrder) => {

                const matchesSearch =
                    !searchValue ||

                    String(
                        productionOrder.productionOrderId
                    )
                        .toLowerCase()
                        .includes(searchValue) ||

                    String(
                        productionOrder.orderNumber || ""
                    )
                        .toLowerCase()
                        .includes(searchValue) ||

                    String(
                        productionOrder.batchNumber || ""
                    )
                        .toLowerCase()
                        .includes(searchValue) ||

                    String(
                        productionOrder.materialId || ""
                    )
                        .toLowerCase()
                        .includes(searchValue);

                const matchesStatus =
                    statusFilter === "ALL" ||
                    productionOrder.status === statusFilter;

                return (
                    matchesSearch &&
                    matchesStatus
                );

            }
        );

    }, [
        productionOrders,
        search,
        statusFilter
    ]);

    return (
        <MainLayout>

            <Box>

                <Typography
                    variant="h4"
                    fontWeight="bold"
                    gutterBottom
                >
                    Production Management
                </Typography>

                <Button
                    variant="contained"
                    onClick={() =>
                        navigate("/production/create")
                    }
                    sx={{ mb: 1 }}
                >
                    + Create Production Order
                </Button>

                <Typography
                    variant="body1"
                    color="text.secondary"
                    sx={{ mb: 3 }}
                >
                    View and manage manufacturing
                    production orders.
                </Typography>

                {!loading && !error && (
                    <Box
                        sx={{
                            display: "flex",
                            gap: 2,
                            mb: 3,
                            flexWrap: "wrap"
                        }}
                    >

                        <TextField
                            label="Search Production Order"
                            placeholder="ID, order, batch or material"
                            value={search}
                            onChange={(event) =>
                                setSearch(
                                    event.target.value
                                )
                            }
                            sx={{
                                minWidth: 300
                            }}
                        />

                        <FormControl
                            sx={{
                                minWidth: 180
                            }}
                        >

                            <InputLabel>
                                Status
                            </InputLabel>

                            <Select
                                value={statusFilter}
                                label="Status"
                                onChange={(event) =>
                                    setStatusFilter(
                                        event.target.value
                                    )
                                }
                            >

                                <MenuItem value="ALL">
                                    All
                                </MenuItem>

                                {statuses.map(
                                    (status) => (

                                        <MenuItem
                                            key={status}
                                            value={status}
                                        >
                                            {status}
                                        </MenuItem>

                                    )
                                )}

                            </Select>

                        </FormControl>

                    </Box>
                )}

                {loading && (
                    <Box
                        sx={{
                            display: "flex",
                            justifyContent: "center",
                            mt: 5
                        }}
                    >
                        <CircularProgress />
                    </Box>
                )}

                {error && (
                    <Alert
                        severity="error"
                        sx={{ mb: 3 }}
                    >
                        {error}
                    </Alert>
                )}

                {!loading && !error && (
                    <TableContainer
                        component={Paper}
                        elevation={2}
                    >

                        <Table>

                            <TableHead>

                                <TableRow>

                                    <TableCell>
                                        ID
                                    </TableCell>

                                    <TableCell>
                                        Order Number
                                    </TableCell>

                                    <TableCell>
                                        Batch Number
                                    </TableCell>

                                    <TableCell>
                                        Material ID
                                    </TableCell>

                                    <TableCell>
                                        Planned Quantity
                                    </TableCell>

                                    <TableCell>
                                        Produced Quantity
                                    </TableCell>

                                    <TableCell>
                                        Unit
                                    </TableCell>

                                    <TableCell>
                                        Status
                                    </TableCell>

                                    <TableCell>
                                        Planned Start
                                    </TableCell>

                                    <TableCell>
                                        Planned End
                                    </TableCell>

                                </TableRow>

                            </TableHead>

                            <TableBody>

                                {filteredProductionOrders.map(
                                    (productionOrder) => (

                                        <TableRow
                                            key={
                                                productionOrder
                                                    .productionOrderId
                                            }
                                            hover
                                        >

                                            <TableCell>
                                                {
                                                    productionOrder
                                                        .productionOrderId
                                                }
                                            </TableCell>

                                            <TableCell>

                                                <Button
                                                    variant="text"
                                                    onClick={() =>
                                                        navigate(
                                                            `/production/${productionOrder.productionOrderId}`
                                                        )
                                                    }
                                                >
                                                    {
                                                        productionOrder
                                                            .orderNumber
                                                    }
                                                </Button>

                                            </TableCell>

                                            <TableCell>
                                                {
                                                    productionOrder
                                                        .batchNumber
                                                }
                                            </TableCell>

                                            <TableCell>
                                                {
                                                    productionOrder
                                                        .materialId
                                                }
                                            </TableCell>

                                            <TableCell>
                                                {
                                                    productionOrder
                                                        .plannedQuantity
                                                }
                                            </TableCell>

                                            <TableCell>
                                                {
                                                    productionOrder
                                                        .producedQuantity ??
                                                    "-"
                                                }
                                            </TableCell>

                                            <TableCell>
                                                {
                                                    productionOrder
                                                        .unit
                                                }
                                            </TableCell>

                                            <TableCell>
                                                {
                                                    productionOrder
                                                        .status
                                                }
                                            </TableCell>

                                            <TableCell>
                                                {
                                                    productionOrder
                                                        .plannedStartDate ??
                                                    "-"
                                                }
                                            </TableCell>

                                            <TableCell>
                                                {
                                                    productionOrder
                                                        .plannedEndDate ??
                                                    "-"
                                                }
                                            </TableCell>

                                        </TableRow>

                                    )
                                )}

                                {filteredProductionOrders.length ===
                                    0 && (

                                    <TableRow>

                                        <TableCell
                                            colSpan={10}
                                            align="center"
                                        >
                                            No production
                                            orders found
                                        </TableCell>

                                    </TableRow>

                                )}

                            </TableBody>

                        </Table>

                    </TableContainer>
                )}

                {!loading && !error && (
                    <Typography
                        variant="body2"
                        color="text.secondary"
                        sx={{ mt: 2 }}
                    >
                        Showing{" "}
                        {filteredProductionOrders.length}{" "}
                        of{" "}
                        {productionOrders.length}{" "}
                        production orders
                    </Typography>
                )}

            </Box>

        </MainLayout>
    );
}

export default ProductionOrderList;