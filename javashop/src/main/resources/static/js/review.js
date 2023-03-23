
async function getList({prodId, page, size, goLast}){
	const result = await axios.get(`/review/list/${prodId}`, {params : {page, size}})
	if(goLast){
		const total = result.data.total
		const lastPage = parseInt(Math.ceil(total/size))
		return getList({prodId:prodId, page:lastPage, size:size})
	}
	return result.data
}

async function addReview(reviewObj){
	const response = await axios.post(`/review/`, reviewObj)
	return response.data
	
}

async function getReview(revId){
	const response = await axios.get(`/review/${revId}`)
	return response.data
}

async function modifyReview(reviewObj) {
	const response = await axios.put(`/review/${reviewObj.revId}`, reviewObj)
	return response.data
}