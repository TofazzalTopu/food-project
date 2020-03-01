<script>
    $(document).ready(function () {
        $("#upload-button").click(function () {
            if (!$('#uploadFile').val()) {
                MessageRenderer.render({
                    "messageBody": "Please select excel data file first.",
                    "messageTitle": "Chart Of Account Upload",
                    "type": "0"
                });
                return false;
            }
            return true;
        });
    });
</script>