import { useEffect, useState } from "react";

import {
    Alert,
    Box,
    Button,
    Card,
    CardContent,
    CircularProgress,
    Dialog,
    DialogActions,
    DialogContent,
    DialogContentText,
    DialogTitle,
    TextField,
    Divider,
    Grid,
    Typography
} from "@mui/material";

import ArrowBackIcon from "@mui/icons-material/ArrowBack";

import { useNavigate, useParams } from "react-router-dom";

import MainLayout from "../../layouts/MainLayout";

import {
    cancelBatch,
    closeBatch,
    completeBatch,
    deleteBatch,
    getBatchById,
    holdBatch,
    markBatchReady,
    resumeBatch,
    startBatch
} from "../../services/batchService";

function BatchDetails() {

    const { id } = useParams();

    const navigate = useNavigate();

    const [batch, setBatch] = useState(null);

    const [loading, setLoading] = useState(true);

    const [error, setError] = useState("");

    const [workflowLoading, setWorkflowLoading] =
    useState(false);

    const [workflowError, setWorkflowError] =
        useState("");

    const [completeDialogOpen, setCompleteDialogOpen] =
        useState(false);

    const [actualQuantity, setActualQuantity] =
        useState("");

    const [deleteDialogOpen, setDeleteDialogOpen] =
    useState(false);

    const [deleting, setDeleting] =
    useState(false);

    useEffect(() => {

        const loadBatch = async () => {

            try {

                setLoading(true);
                setError("");

                const data = await getBatchById(id);

                console.log("Batch details loaded:", data);

                setBatch(data);

            } catch (error) {

                console.error(
                    "Failed to load batch:",
                    error.response?.data || error.message
                );

                setError("Unable to load batch details.");

            } finally {

                setLoading(false);

            }
        };

        loadBatch();

    }, [id]);

    const handleDelete = async () => {

    try {

        setDeleting(true);
        setError("");

        await deleteBatch(id);

        navigate("/batches");

    } catch (error) {

        console.error(
            "Failed to delete batch:",
            error.response?.data ||
            error.message
        );

        setError(
            error.response?.data?.message ||
            "Failed to delete batch."
        );

        setDeleteDialogOpen(false);

    } finally {

        setDeleting(false);

    }
    };

    const handleWorkflowAction = async (action) => {

    try {

        setWorkflowLoading(true);

        setWorkflowError("");

        let updatedBatch;

        switch (action) {

            case "READY":
                updatedBatch =
                    await markBatchReady(id);
                break;

            case "START":
                updatedBatch =
                    await startBatch(id);
                break;

            case "HOLD":
                updatedBatch =
                    await holdBatch(id);
                break;

            case "RESUME":
                updatedBatch =
                    await resumeBatch(id);
                break;

            case "CLOSE":
                updatedBatch =
                    await closeBatch(id);
                break;

            case "CANCEL":
                updatedBatch =
                    await cancelBatch(id);
                break;

            default:
                return;
        }

        console.log(
            "Workflow action successful:",
            updatedBatch
        );

        setBatch(updatedBatch);

    } catch (error) {

        console.error(
            "Workflow action failed:",
            error.response?.data ||
            error.message
        );

        setWorkflowError(
            error.response?.data?.message ||
            "Workflow action failed."
        );

    } finally {

        setWorkflowLoading(false);

    }
    };

    const handleComplete = async () => {

    try {

        setWorkflowLoading(true);

        setWorkflowError("");

        const updatedBatch =
            await completeBatch(
                id,
                Number(actualQuantity)
            );

        console.log(
            "Batch completed:",
            updatedBatch
        );

        setBatch(updatedBatch);

        setCompleteDialogOpen(false);

        setActualQuantity("");

    } catch (error) {

        console.error(
            "Failed to complete batch:",
            error.response?.data ||
            error.message
        );

        setWorkflowError(
            error.response?.data?.message ||
            "Failed to complete batch."
        );

    } finally {

        setWorkflowLoading(false);

    }
    };

    return (
        <MainLayout>

            <Box>

                <Button
                    startIcon={<ArrowBackIcon />}
                    onClick={() => navigate("/batches")}
                    sx={{ mb: 2 }}
                >
                    Back to Batches
                </Button>

                <Typography
                    variant="h4"
                    fontWeight="bold"
                    gutterBottom
                >
                    Batch Details
                </Typography>

                <Button
                    variant="contained"
                    onClick={() =>
                        navigate(`/batches/${id}/edit`)
                    }
                >
                    Edit Batch
                </Button>

                <Button
                    variant="outlined"
                    color="error"
                    onClick={() =>
                        setDeleteDialogOpen(true)
                    }
                >
                    Delete Batch
                </Button>

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
                        sx={{ mt: 2 }}
                    >
                        {error}
                    </Alert>
                )}

                {!loading && !error && batch && (

                    <Card
                        elevation={2}
                        sx={{ mt: 3 }}
                    >

                        <CardContent>

                            <Typography
                                variant="h5"
                                fontWeight="bold"
                                gutterBottom
                            >
                                {batch.batchNumber}
                            </Typography>

                            <Divider sx={{ mb: 3 }} />

                            <Grid
                                container
                                spacing={3}
                            >

                                <Grid
                                    size={{
                                        xs: 12,
                                        sm: 6,
                                        md: 4
                                    }}
                                >

                                    <Typography
                                        variant="body2"
                                        color="text.secondary"
                                    >
                                        Batch ID
                                    </Typography>

                                    <Typography variant="h6">
                                        {batch.batchId}
                                    </Typography>

                                </Grid>

                                <Grid
                                    size={{
                                        xs: 12,
                                        sm: 6,
                                        md: 4
                                    }}
                                >

                                    <Typography
                                        variant="body2"
                                        color="text.secondary"
                                    >
                                        Batch Number
                                    </Typography>

                                    <Typography variant="h6">
                                        {batch.batchNumber}
                                    </Typography>

                                </Grid>

                                <Grid
                                    size={{
                                        xs: 12,
                                        sm: 6,
                                        md: 4
                                    }}
                                >

                                    <Typography
                                        variant="body2"
                                        color="text.secondary"
                                    >
                                        Status
                                    </Typography>

                                    <Typography
                                        variant="h6"
                                    >
                                        {batch.status}
                                    </Typography>

                                </Grid>

                            </Grid>

                        </CardContent>
                                  
        <CardContent>

            <Typography
                variant="h6"
                fontWeight="bold"
                gutterBottom
            >
                Batch Workflow
            </Typography>

            {workflowError && (
                <Alert
                    severity="error"
                    sx={{ mb: 2 }}
                >
                    {workflowError}
                </Alert>
            )}

            <Box
                sx={{
                    display: "flex",
                    gap: 2,
                    flexWrap: "wrap"
                }}
            >

                {batch.status === "CREATED" && (
                    <Button
                        variant="contained"
                        disabled={workflowLoading}
                        onClick={() =>
                            handleWorkflowAction("READY")
                        }
                    >
                        Mark Ready
                    </Button>
                )}

                {batch.status === "READY" && (
                    <Button
                        variant="contained"
                        disabled={workflowLoading}
                        onClick={() =>
                            handleWorkflowAction("START")
                        }
                    >
                        Start Batch
                    </Button>
                )}

                {batch.status === "IN_PROGRESS" && (
                    <>

                        <Button
                            variant="outlined"
                            disabled={workflowLoading}
                            onClick={() =>
                                handleWorkflowAction("HOLD")
                            }
                        >
                            Hold
                        </Button>

                        <Button
                            variant="contained"
                            disabled={workflowLoading}
                            onClick={() =>
                                setCompleteDialogOpen(true)
                            }
                        >
                            Complete
                        </Button>

                    </>
                )}

                {batch.status === "ON_HOLD" && (
                    <Button
                        variant="contained"
                        disabled={workflowLoading}
                        onClick={() =>
                            handleWorkflowAction("RESUME")
                        }
                    >
                        Resume
                    </Button>
                )}

                {batch.status === "COMPLETED" && (
                    <Button
                        variant="contained"
                        disabled={workflowLoading}
                        onClick={() =>
                            handleWorkflowAction("CLOSE")
                        }
                    >
                        Close Batch
                    </Button>
                )}

                {[
                    "CREATED",
                    "READY",
                    "IN_PROGRESS",
                    "ON_HOLD"
                ].includes(batch.status) && (

                    <Button
                        color="error"
                        variant="outlined"
                        disabled={workflowLoading}
                        onClick={() =>
                            handleWorkflowAction("CANCEL")
                        }
                    >
                        Cancel Batch
                    </Button>

                )}

            </Box>

        </CardContent>

                    </Card>
                    
                )}

            </Box>

                <Dialog
    open={completeDialogOpen}
    onClose={() => {
        if (!workflowLoading) {
            setCompleteDialogOpen(false);
        }
    }}
>

    <DialogTitle>
        Complete Batch
    </DialogTitle>

    <DialogContent>

        <DialogContentText sx={{ mb: 2 }}>
            Enter the actual quantity produced for{" "}
            <strong>
                {batch?.batchNumber}
            </strong>.
        </DialogContentText>

        <TextField
            fullWidth
            required
            autoFocus
            type="number"
            label="Actual Quantity"
            value={actualQuantity}
            onChange={(event) =>
                setActualQuantity(
                    event.target.value
                )
            }
            slotProps={{
                htmlInput: {
                    min: 0,
                    step: "any"
                }
            }}
        />

    </DialogContent>

    <DialogActions>

        <Button
            onClick={() =>
                setCompleteDialogOpen(false)
            }
            disabled={workflowLoading}
        >
            Cancel
        </Button>

        <Button
            variant="contained"
            onClick={handleComplete}
            disabled={
                workflowLoading ||
                !actualQuantity
            }
        >
            {workflowLoading
                ? "Completing..."
                : "Complete Batch"}
        </Button>

    </DialogActions>

</Dialog>

                <Dialog
    open={deleteDialogOpen}
    onClose={() => {
        if (!deleting) {
            setDeleteDialogOpen(false);
        }
    }}
>

                <DialogTitle>
                    Delete Batch
                </DialogTitle>

                <DialogContent>

                    <DialogContentText>
                        Are you sure you want to delete batch{" "}
                        <strong>
                            {batch?.batchNumber}
                        </strong>
                        ?
                    </DialogContentText>

                    <DialogContentText sx={{ mt: 2 }}>
                        This action cannot be undone.
                    </DialogContentText>

                </DialogContent>

                <DialogActions>

                    <Button
                        onClick={() =>
                            setDeleteDialogOpen(false)
                        }
                        disabled={deleting}
                    >
                        Cancel
                    </Button>

                    <Button
                        color="error"
                        variant="contained"
                        onClick={handleDelete}
                        disabled={deleting}
                    >
                        {deleting
                            ? "Deleting..."
                            : "Delete"}
                    </Button>

                </DialogActions>

            </Dialog>

        </MainLayout>
    );
}

export default BatchDetails;