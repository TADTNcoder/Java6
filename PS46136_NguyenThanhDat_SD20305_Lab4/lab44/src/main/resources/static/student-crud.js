const host = "https://lab4-b50a2-default-rtdb.firebaseio.com/m";

const $api = {
    key: null,

    get student() {
        return {
            id: $("#id").val().trim(),
            name: $("#name").val().trim(),
            mark: Number($("#mark").val()),
            gender: $("#male").prop("checked")
        };
    },

    set student(e) {
        $("#id").val(e.id || "");
        $("#name").val(e.name || "");
        $("#mark").val(e.mark ?? "");
        $("#male").prop("checked", e.gender === true);
        $("#female").prop("checked", e.gender === false);
    },

    validate() {
        const e = this.student;

        if (!e.id) {
            alert("Chưa nhập mã sinh viên!");
            return false;
        }

        if (!e.name) {
            alert("Chưa nhập họ tên!");
            return false;
        }

        if (isNaN(e.mark)) {
            alert("Điểm không hợp lệ!");
            return false;
        }

        return true;
    },

    // 🔥 LOAD DATA
    fillToTable() {
        const url = `${host}/students.json`;

        axios.get(url).then(resp => {
            $("tbody").empty();

            if (!resp.data) return;

            Object.keys(resp.data).forEach(key => {
                const e = resp.data[key];

                const tr = `
                    <tr>
                        <td>${e.id ?? ""}</td>
                        <td>${e.name ?? ""}</td>
                        <td>${e.mark ?? ""}</td>
                        <td>${e.gender ? "Male" : "Female"}</td>
                        <td>
                            <a href="#" onclick="$api.edit('${key}')">Edit</a> |
                            <a href="#" onclick="$api.delete('${key}')">Delete</a>
                        </td>
                    </tr>
                `;
                $("tbody").append(tr);
            });
        }).catch(error => {
            console.error(error);
            alert("Lỗi tải dữ liệu!");
        });
    },

    // 🔥 EDIT
    edit(key) {
        this.key = key.trim();
        const url = `${host}/students/${this.key}.json`;

        axios.get(url).then(resp => {
            this.student = resp.data || {};
        }).catch(error => {
            console.error(error);
            alert("Lỗi load sinh viên!");
        });
    },

    // 🔥 CREATE
    create() {
        if (!this.validate()) return;

        const url = `${host}/students.json`;

        axios.post(url, this.student).then(resp => {
            this.fillToTable();
            this.reset();
            alert("Thêm thành công!");
        }).catch(error => {
            console.error(error);
            alert("Lỗi thêm!");
        });
    },

    // 🔥 UPDATE
    update() {
        if (!this.key) {
            alert("Bấm Edit trước!");
            return;
        }

        if (!this.validate()) return;

        const url = `${host}/students/${this.key}.json`;

        axios.put(url, this.student).then(resp => {
            this.fillToTable();
            this.reset();
            alert("Cập nhật thành công!");
        }).catch(error => {
            console.error(error);
            alert("Lỗi update!");
        });
    },

    // 🔥 DELETE
    delete(key) {
        const finalKey = key || this.key;

        if (!finalKey) {
            alert("Chưa chọn sinh viên!");
            return;
        }

        if (!confirm("Xóa không?")) return;

        const url = `${host}/students/${finalKey}.json`;

        axios.delete(url).then(resp => {
            this.fillToTable();
            this.reset();
            alert("Đã xóa!");
        }).catch(error => {
            console.error(error);
            alert("Lỗi xóa!");
        });
    },

    reset() {
        this.key = null;
        this.student = {};
        $("#male").prop("checked", false);
        $("#female").prop("checked", false);
    }
};

// 🔥 QUAN TRỌNG: LOAD SAU KHI HTML READY
$(document).ready(function () {
    $api.fillToTable();
});