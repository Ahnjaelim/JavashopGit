
async function addWish(wishObj){
	const response = await axios.post(`/wishrest/`, wishObj)
	return response.data
}

async function checkWish(wishObj){
	const response = await axios.post(`/wishrest/check`, wishObj)
	return response.data
}

async function removeWish(wid){
	const response = await axios.delete(`/wishrest/${wid}`)
	return response.data
}