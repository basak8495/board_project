<template>
  <main>
		<el-row class="content" type="flex" justify="center">
			<el-row>
				<h3 class="title">내정보</h3>
				<el-form :model="myinfoForm" label-width="120px">
					<el-form-item label="아이디">
						<el-input v-model="myinfoForm.loginId" :disabled="true"></el-input>
					</el-form-item>
					<el-form-item label="비밀번호">
						<el-input v-model="myinfoForm.password" show-password :disabled="true"></el-input>
					</el-form-item>
					<el-form-item label="닉네임">
						<el-input v-model="myinfoForm.nickname"></el-input>
					</el-form-item>
					<el-form-item label="이름">
						<el-input v-model="myinfoForm.name"></el-input>
					</el-form-item>
					<el-form-item label="이메일">
						<el-input v-model="myinfoForm.email"></el-input>
					</el-form-item>
					<el-form-item label="핸드폰 번호">
						<el-input v-model="myinfoForm.phoneNumber"></el-input>
					</el-form-item>
					<el-col class="icon-area">
						<el-button class="el-icon-edit" circle @click="memberModify()"></el-button>
						<el-button type="info" icon="el-icon-delete" circle @click="memberRemove()"></el-button>
					</el-col>
				</el-form>
			</el-row>
		</el-row>
	</main>
</template>

<script>
	import jwt_decode from "jwt-decode";

	export default {
		data() {
			return {
				myinfoForm: {}
			}
		},
		mounted() {
			const paramsMemberId = this.$route.params.memberId; // 주소의 memberId 값

			const jwtToken = localStorage.getItem("jwtToken"); // 사용자 토큰의 memberId 값
			const decodedToken = jwt_decode(jwtToken);
			const memberId = decodedToken.memberId;

			if (paramsMemberId == memberId) { // 주소와 사용자가 일치하면 진행
				// 내정보 불러오기
				this.$axios.get("/api/myinfo/" + memberId)
					.then(response => {
						this.myinfoForm = response.data;
						this.myinfoForm.password = "";
					})
					.catch(error => {
						alert("내정보를 가져오는 도중 오류가 발생했습니다.");
						console.error("내정보를 가져오는 도중 오류가 발생했습니다.", error);
					});
			} else {
				alert("사용자가 일치하지 않습니다.");
				console.log("사용자가 일치하지 않습니다.");
			}
		},
		methods: {
			memberModify() {
				const paramsMemberId = this.$route.params.memberId; // 주소의 memberId 값

				const jwtToken = localStorage.getItem("jwtToken"); // 사용자 토큰의 memberId 값
				const decodedToken = jwt_decode(jwtToken);
				const memberId = decodedToken.memberId;

				if (paramsMemberId == memberId) { // 주소와 사용자가 일치하면 진행
					// 내정보 수정
					this.$axios.put("/api/myinfo/" + memberId, this.myinfoForm)
						.then(response => {
							alert("내정보가 수정되었습니다!");
							console.log("내정보가 수정되었습니다!");
							this.$router.push("/boardlist")
						})
						.catch(error => {
							alert("내정보 수정에 실패했습니다.");
							console.error("내정보 수정에 실패했습니다.", error);
						})
				} else {
					alert("사용자가 일치하지 않습니다.");
					console.log("사용자가 일치하지 않습니다.");
				}
			},
			memberRemove() {
				const paramsMemberId = this.$route.params.memberId; // 주소의 memberId 값

				const jwtToken = localStorage.getItem("jwtToken"); // 사용자 토큰의 memberId 값
				const decodedToken = jwt_decode(jwtToken);
				const memberId = decodedToken.memberId;

				if (paramsMemberId == memberId) { // 주소와 사용자가 일치하면 진행
					const confirmResult = confirm("정말로 회원탈퇴를 진행하시겠습니까?");
					if (confirmResult) {
						console.log("사용자 토큰이 인증되었습니다.")
						// 회원탈퇴
						this.$axios.delete('/api/myinfo/' + memberId)
							.then(response => {
								alert("회원탈퇴가 완료되었습니다.")
								console.log("회원탈퇴가 완료되었습니다.");
								localStorage.removeItem("jwtToken");
								this.$router.push("/");
        				window.location.reload();
							})
							.catch(error => {
								alert("회원탈퇴에 실패했습니다.")
								console.error("회원탈퇴에 실패했습니다.", error);
							})
					} else {
						alert("회원 탈퇴가 취소되었습니다.")
						console.log("회원 탈퇴가 취소되었습니다.");
					}
				} else {
					alert("사용자가 일치하지 않습니다.");
					console.log("사용자가 일치하지 않습니다.");
				}
			}
		}
	};
</script>

<style scoped>
	.content {
		margin-top: 2%;
	}
	.title {
    text-align: center;
  }
	.icon-area {
		text-align: right;
	}
	.el-icon-edit {
    background-color: #e99a51;
    color: white;
  }
</style>